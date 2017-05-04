package ca.on.sl.comp208.lab2;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

/**
 * Created by rortelli27 on 1/30/2017.
 */

/**
 * This class handles the view
 */
public class DrawView extends SurfaceView implements SurfaceHolder.Callback {

    /**
     * Variables
     */
    private drawThread thread;
    private MainActivity activity;
    private Point size;
    private GestureDetector gestureDetector;
    private DisplayMetrics metrics = new DisplayMetrics();
    private Display display;
    private WindowManager windowManager;

    /**
     * Constructor for the DrawView class, initializes the gestureDetector, windowManager, display and metrics and grabs the activity and holder
     * @param context
     * @param attrs
     */
    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        activity = (MainActivity) context;
        getHolder().addCallback(this);
        gestureDetector = new GestureDetector(context, new GestureListener());
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        display.getMetrics(metrics);
    }

    /**
     * onTouchEvent, activates the gestureDetector when touched
     * @param event
     * @return
     */
    public boolean onTouchEvent(MotionEvent event)
    {
        return gestureDetector.onTouchEvent(event);
    }

    /**
     * surfaceCreated, starts the thread
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.start();
    }

    /**
     * surfaceChanged, reports the changed width and height
     * @param holder
     * @param format
     * @param width
     * @param height
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        size = new Point(width, height);
    }

    /**
     * surfaceDestroyed, pauses the thread and joins it
     * @param holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        thread.setRunningThread(false);

        try
        {
            thread.join();
        }

        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * getSize, returns the width and height in a Point
     * @return
     */
    public Point getSize()
    {
        return size;
    }

    /**
     * setThread, sets the thread
     * @param thread
     */
    public void setThread(drawThread thread) {
        this.thread = thread;
    }

    /**
     * getMetrics, gets the screen size
     * @return
     */
    public DisplayMetrics getMetrics()
    {
        return metrics;
    }

    /**
     * Private inner class that handles the gestures
     */
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        /**
         * onDown
         * @param event
         * @return
         */
        @Override
        public boolean onDown(MotionEvent event)
        {
            return true;
        }

        /**
         * onSingleTapUp, handles adding a single cell to the grid
         * @param event
         * @return
         */
        @Override
        public boolean onSingleTapUp(MotionEvent event)
        {
            thread.addCell(event.getX(), event.getY());

            return true;
        }

        /**
         * onDoubleTap, handles adding a single Glider to the grid
         * @param event
         * @return
         */
        @Override
        public boolean onDoubleTap(MotionEvent event)
        {
            synchronized (event)
            {
                thread.addGlider(event.getX(), event.getY());
            }

            return true;
        }

    }

}
