package ca.on.sl.comp208.lab1a;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    /**
     * Variables
     */
    private int score;
    private int guesses;
    private int topScore;
    private ArrayList<Integer> images = new ArrayList<Integer>(){{
        add(R.mipmap.ietroll);
        add(R.mipmap.meme);
        add(R.mipmap.trollface);
        add(R.mipmap.trollfacetwo);
        add(R.mipmap.nopememe);
        add(R.mipmap.happytroll);
        add(R.mipmap.ubermeme);
        add(R.mipmap.whynomeme);
    }};
    Random rand = new Random();
    int generatedNumber;
    ImageButton btn;
    ImageButton btnPrevFail;
    ImageButton btnPrev;
    final Handler handler = new Handler();
    StopWatch time = new StopWatch();
    Set<String> fiveScores = new HashSet<String>();
    String stringGuess;
    String finalGuesses;
    ArrayList<String> scoreList;

    /**
     * onCreate, setups and initializes variables, starts the timer thread, loads the scores from pref
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        //editor.clear();
        //editor.putInt("imageId", -1);
        //editor.putInt("buttonId", -1);
        //editor.commit();

        time.startTiming();

        //TextView timer = (TextView) findViewById(R.id.textView8);

        Thread t = new Thread() {

            TextView timer = (TextView) findViewById(R.id.textView8);

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timer.setText(time.stopTiming());
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();

        score = 0;
        guesses = 0;

        try
        {
            if(prefs.getStringSet("fiveScores", null) != null)
            {
                fiveScores = prefs.getStringSet("fiveScores", null);
            }
        }

        catch (Exception e)
        {
            Log.i("fiveScores", "fiveScores is null");
        }

        /*fiveScores.add("Score: " + "80" + " #Tries: " + "13" + " Time: " + "02:45");
        fiveScores.add("Score: " + "80" + " #Tries: " + "14" + " Time: " + "02:48");
        fiveScores.add("Score: " + "80" + " #Tries: " + "34" + " Time: " + "03:45");
        fiveScores.add("Score: " + "80" + " #Tries: " + "24" + " Time: " + "01:45");
        fiveScores.add("Score: " + "80" + " #Tries: " + "67" + " Time: " + "02:40");*/

        scoreList = new ArrayList<String>(fiveScores);

        /*Collections.sort(scoreList);

        for(Iterator<String> ctr = scoreList.iterator(); ctr.hasNext(); ) {
            String item = ctr.next();
            Log.i("Score: ", item);
        }

        Collections.sort(scoreList);

        Log.i("Before Loop", String.valueOf(scoreList.size()));

        for(int ctr = 3; ctr < scoreList.size(); ctr++)
        {
            scoreList.remove(ctr);
            Log.i("Removed", "Removed");
        }

        Log.i("After Loop", String.valueOf(scoreList.size()));

        for(Iterator<String> ctr = scoreList.iterator(); ctr.hasNext(); ) {
            String item = ctr.next();
            Log.i("subList: ", item);
        }*/
    }

    /**
     * onStop, runs when game ends
     */
    @Override
    protected void onStop()
    {
        super.onStop();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("msg", "Game Ended");
        editor.commit();
    }

    /**
     * clicked, takes a button click action and generates a image based on a random number generated.
     * If a match is found it increments the socre and disables the matched buttons, if there is no match it flips the cards back over and resets them.
     * When all cards are matched it saves the score in prefs and navigates to the ScoreActivity.
     * @param view
     * @throws InterruptedException
     */
    public void clicked(View view) throws InterruptedException {
        btn = (ImageButton) view;

        generatedNumber = rand.nextInt((images.size())) + 0;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();

        Log.i("generatedNumber", String.valueOf(generatedNumber));
        Log.i("generatedNumber", String.valueOf(images.get(generatedNumber)));
        Log.i("Button Id", String.valueOf(btn.getId()));
        Log.i("prefs Id", String.valueOf(prefs.getInt("imageId", -1)));
        Log.i("score", String.valueOf(score));

        btn.setImageResource(images.get(generatedNumber));

        if (prefs.getInt("imageId", -1) == -1) {
            editor.putInt("imageId", images.get(generatedNumber));
            editor.putInt("buttonId", btn.getId());
            Log.i("First Click", "First Click");
        } else if (prefs.getInt("imageId", -1) == images.get(generatedNumber)) {

            if (prefs.getInt("buttonId", -1) == btn.getId())
            {
                return;
            }

            btnPrev = (ImageButton) findViewById(prefs.getInt("buttonId", -1));
            btn.setTag("face_up");
            btnPrev.setTag("face_up");
            btn.setEnabled(false);
            btnPrev.setEnabled(false);
            images.remove(generatedNumber);
            score += 10;
            TextView editScore = (TextView) findViewById(R.id.textView3);
            editScore.setText(String.valueOf(score));
            editor.putInt("imageId", -1);
            editor.putInt("buttonId", -1);
            editor.putString("match", "You Matched!!!!!");
            Log.i("Matched", "Matched");

            if (images.isEmpty())
            {
                Log.i("fiveScores Dumped", String.valueOf(score) + " " + String.valueOf(guesses) + " " + time.stopTiming());

                if(guesses < 10)
                {
                    stringGuess = String.valueOf(guesses);
                    finalGuesses = ("00" + stringGuess).substring(stringGuess.length());

                    Log.i("Added 0", finalGuesses);

                }
                else
                {
                    finalGuesses = String.valueOf(guesses);
                    Log.i("Didn't Add 0", finalGuesses);
                }

                fiveScores.add("Score: " + String.valueOf(score) + " #Tries: " + finalGuesses + " Time: " + time.stopTiming());

                scoreList = new ArrayList<String>(fiveScores);

                Collections.sort(scoreList);

                for(int ctr = 3; ctr < scoreList.size(); ctr++)
                {
                    scoreList.remove(ctr);
                    Log.i("Removed", "Removed");
                }

                fiveScores.addAll(scoreList);

                editor.putStringSet("fiveScores", fiveScores);
                editor.commit();

                Intent scorePage = new Intent(this, ScoreActivity.class);

                startActivity(scorePage);
            }
        }
        else
        {
            btnPrevFail = (ImageButton) findViewById(prefs.getInt("buttonId", -1));

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    btn.setImageResource(R.mipmap.ic_launcher);
                    btnPrevFail.setImageResource(R.mipmap.ic_launcher);
                }
            }, 2000);

            editor.putInt("imageId", -1);
            editor.putInt("buttonId", -1);

            guesses += 1;
            TextView editGuesses = (TextView) findViewById(R.id.textView5);
            editGuesses.setText(String.valueOf(guesses));
            Log.i("Guesses", String.valueOf(guesses));
            Log.i("btnPrevFail", String.valueOf(findViewById(prefs.getInt("buttonId", -1))));
            Log.i("Image Id", String.valueOf(prefs.getInt("imageId", -1)));
            Log.i("NoMatch", "No Match");
        }

        editor.commit();
    }
}
