package ca.on.sl.comp208.assignment3realortelli;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**
     * Variables
     */
    private ListView listView;
    private Cursor cursor = null;
    private ArrayAdapter adapter;
    private List<Items> items = new ArrayList<>();
    private URL pictureUrl;
    private Bitmap bmp;
    private View view;
    private static final long MIN_CLICK_INTERVAL = 600;
    private long mLastClickTime;
    private static boolean isViewClicked = false;
    private boolean checkBool = false;
    public static int buttonClicked;
    public static int entityID;

    /**
     * onCreate, Initializes the Adapter class and grabs the listView
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        listView.setBackgroundColor(Color.GRAY);
        view = this.getWindow().getDecorView();
        view.setBackgroundColor(Color.BLACK);
        adapter = new Adapter(this, R.layout.item_layout, items);
    }

    /**
     * click, handles the New, Popular, Like and Dislike buttons.
     * Also calls the HttpConnectionTask
     * @param view
     */
    public void click(View view)
    {
        long currentClickTime = SystemClock.uptimeMillis();
        long elapsedTime = currentClickTime - mLastClickTime;

        mLastClickTime = currentClickTime;

        if(elapsedTime <= MIN_CLICK_INTERVAL)
        {
            return;
        }

        if(!isViewClicked)
        {
            isViewClicked = true;
            startTimer();

            synchronized(view)
            {
                switch(view.getId())
                {
                    case R.id.Popular:
                        buttonClicked = 1;
                        checkBool = true;
                        break;
                    case R.id.New:
                        buttonClicked = 2;
                        checkBool = true;
                        break;
                    case R.id.Like:
                        buttonClicked = 3;
                        View parentRowLike = (View) view.getParent();
                        ListView listViewLike = (ListView) parentRowLike.getParent();
                        final int positionLike = listViewLike.getPositionForView(parentRowLike);
                        entityID = (int) listViewLike.getAdapter().getView(positionLike, null, listViewLike).findViewById(R.id.Like).getTag();
                        Toast.makeText(this, "Liked", Toast.LENGTH_SHORT).show();
                        checkBool = false;
                        break;
                    case R.id.Dislike:
                        buttonClicked = 4;
                        View parentRowsDislike = (View) view.getParent();
                        ListView listViewDislike = (ListView) parentRowsDislike.getParent();
                        final int positionDislike = listViewDislike.getPositionForView(parentRowsDislike);
                        entityID = (int) listViewDislike.getAdapter().getView(positionDislike, null, listViewDislike).findViewById(R.id.Dislike).getTag();
                        Toast.makeText(this, "Disliked", Toast.LENGTH_SHORT).show();
                        checkBool = false;
                        break;
                    default:
                        break;
                }

                startTask();
            }
        }
        else
        {
            return;
        }
    }

    /**
     * startTimer, A timer for stopping multiple clicks
     */
    private void startTimer()
    {
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {

            @Override
            public void run()
            {
                isViewClicked = false;
            }
        }, 600);
    }

    /**
     * startTask, Starts the HttpConnectionTask
     */
    private void startTask()
    {
        if(NetworkUtls.networkAvailable(this))
        {
            HttpConnectionTask task = new HttpConnectionTask();
            task.execute();
        }
    }

    /**
     * HttpConnectionTaks Class, Handles the network load with a AsyncTask.
     * Gets a result(cursor) from MemeProvider and adds the results to a List,
     * along with getting the images from the URL's
     */
    private class HttpConnectionTask extends AsyncTask<String, String, Void>
    {
        private String[] projection = MemeContract.COLUMN_RESULT;
        private ContentResolver resolver = getContentResolver();

        @Override
        protected Void doInBackground(String... params) {

            Uri uri = MemeContract.CONTENT_URI;
            cursor = resolver.query(uri, projection, null, null, null);

            if(cursor != null)
            {
                cursor.moveToFirst();

                for (int ctr = 0; ctr < cursor.getCount(); ctr++)
                {
                    try
                    {
                        pictureUrl = new URL(cursor.getString(2));
                        bmp = BitmapFactory.decodeStream(pictureUrl.openConnection().getInputStream());
                    }
                    catch(MalformedURLException e)
                    {
                        e.printStackTrace();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                    items.add(new Items(bmp, "Name: " + cursor.getString(0) + '\n' +
                            "Ranking: " + cursor.getString(1) + '\n' +
                            "Votes: " + cursor.getString(3), cursor.getInt(4)));

                    cursor.moveToNext();
                }

                publishProgress();
            }

            return null;
        }

        /**
         * onPreExecute, Handles any prerequisites before executing
         */
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            if(checkBool)
            {
                adapter.clear();
            }
        }

        /**
         * onProgressUpdate, Updates the listView with setting the adapter
         * @param values
         */
        @Override
        protected void onProgressUpdate(String... values)
        {
            super.onProgressUpdate(values);


            listView.setAdapter(adapter);
        }
    }

}
