package ca.on.sl.comp208.lab2;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.Toast;

/**
 * This class is the main activity
 */
public class MainActivity extends AppCompatActivity implements SensorEventListener{

    /**
     * Variables
     */
    private DrawView view;
    private GameModel model = new GameModel();
    private drawThread thread;
    private SensorManager sensorManager;
    private Sensor accSensor;
    private static final float GRAVITY = 2.7F;
    private static final int TIME_MS = 500;
    private static final int RESET_TIME_MS = 1500;
    private long timeStamp;
    private int shakeCount;

    /**
     * onCreate, initailizes the classes and senors
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = (DrawView)findViewById(R.id.canvas);
        thread = new drawThread(model, view);
        view.setThread(thread);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    /**
     * onCreateOptionsMenu, creates the options for the menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        SubMenu colours = menu.addSubMenu("Colours");
        SubMenu tools = menu.addSubMenu("Tools");

        colours.add("Gray");
        colours.add("Red");
        colours.add("Cyan");
        colours.add("Blue");

        tools.add("Repopulate Grid");
        tools.add("Clear Grid");

        return true;
    }

    /**
     * onOptionsItemSelected, handles the option selected by the user
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getTitle().toString())
        {
            case "Gray":
                thread.setPaintRect(Color.GRAY);
                Toast.makeText(this, "Colour Changed", Toast.LENGTH_LONG).show();
                break;
            case "Red":
                thread.setPaintRect(Color.RED);
                Toast.makeText(this, "Colour Changed", Toast.LENGTH_LONG).show();
                break;
            case "Cyan":
                thread.setPaintRect(Color.CYAN);
                Toast.makeText(this, "Colour Changed", Toast.LENGTH_LONG).show();
                break;
            case "Blue":
                thread.setPaintRect(Color.BLUE);
                Toast.makeText(this, "Colour Changed", Toast.LENGTH_LONG).show();
                break;
            case "Repopulate Grid":
                model.populateGrid();
                Toast.makeText(this, "Populated", Toast.LENGTH_LONG).show();
                break;
            case "Clear Grid":
                model.clearArray();
                Toast.makeText(this, "Cleared", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * onSensorChanged, handles the accelerometer and activates if the movement is within spec
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float gX = x / SensorManager.GRAVITY_EARTH;
            float gY = y / SensorManager.GRAVITY_EARTH;
            float gZ = z / SensorManager.GRAVITY_EARTH;

            float movement = (float)Math.sqrt(gX * gX + gY * gY + gZ * gZ);

            if(movement > GRAVITY) {

                final long currentTime = System.currentTimeMillis();

                if(timeStamp + TIME_MS > currentTime)
                {
                    return;
                }

                if(timeStamp + RESET_TIME_MS < currentTime)
                {
                    shakeCount = 0;

                    model.shakeScreen();

                    Toast.makeText(this, "Screen Shaked", Toast.LENGTH_SHORT).show();
                }

                timeStamp = currentTime;

                shakeCount++;
            }
        }
    }

    /**
     * onResume, registers the sensor when resumed
     */
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accSensor,	SensorManager.SENSOR_DELAY_UI);
    }

    /**
     * onPause, unregisters the sensor
     */
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    /**
     * onStop
     */
    @Override
    protected void onStop()
    {
        super.onStop();
    }

    /**
     * onRestart
     */
    @Override
    protected void onRestart()
    {
        super.onRestart();
    }

    /**
     * onStart, activates the thread
     */
    @Override
    protected void onStart()
    {
        super.onStart();
        thread.setRunningThread(true);
    }

    /**
     * onDestroy
     */
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    /**
     * onSaveInstanceState
     * @param state
     */
    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
    }

    /**
     * onRestoreInstanceState
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * onAccuracyChanged
     * @param sensor
     * @param accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
