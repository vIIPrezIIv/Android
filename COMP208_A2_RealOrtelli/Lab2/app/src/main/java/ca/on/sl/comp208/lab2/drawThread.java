package ca.on.sl.comp208.lab2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceHolder;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Created by Ray on 2017-02-09.
 */

/**
 * This class is the thread that handles drawing the grid
 */
public class drawThread extends Thread{

    /**
     * Variables
     */
    private Paint paint = new Paint();
    private Paint paintRect = new Paint();
    private GameModel model;
    private Canvas canvas = null;
    private DrawView drawView;
    private int cols;
    private int rows;
    private float cellWidth;
    private float cellHeight;
    private boolean runningThread;
    private long lastUpdated;
    public final static long MIN_TIME = 150;
    private Bitmap grid;
    private char quad = 'A';
    private AtomicBoolean atomicBoolean = new AtomicBoolean();

    /**
     * Constructor for the drawThread class, initializes the colours, thread, atomicbool
     * and grabs the cols & rows. Also sets the view and model
     * @param model
     * @param view
     */
    public drawThread(GameModel model, DrawView view)
    {
        this.model = model;
        this.cols = model.getCols();
        this.rows = model.getRows();
        this.drawView = view;
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(5);
        paintRect.setColor(Color.GRAY);
        paintRect.setStrokeWidth(5);
        runningThread = true;
        atomicBoolean.set(false);
    }

    /**
     * run, main function that loops and handles the overall application process
     */
    @Override
    public void run()
    {
        super.run();
        SurfaceHolder holder = drawView.getHolder();

        while(runningThread)
        {
            if(atomicBoolean.get() == false)
            {
                synchronized (holder) {
                    canvas = holder.lockCanvas();

                    if (canvas == null)
                    {
                        continue;
                    }

                    if (grid == null)
                    {
                        grid = cellDrawer(drawView.getSize());
                        model.populateGrid();
                    }

                    drawRectangles(canvas);

                    model.nextGeneration();

                    try
                    {
                        FPSThrottle();
                    }

                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }

                }

                holder.unlockCanvasAndPost(canvas);

            }
            else
            {
                atomicBoolean.set(false);
            }
        }
    }

    /**
     * cellDrawer, makes a bitmap and draws the row & column lines
     * @param size
     * @return
     */
    public Bitmap cellDrawer(Point size)
    {
        Bitmap bitmap = Bitmap.createBitmap(size.x, size.y, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.BLACK);

        cellWidth = (float) size.x / cols;
        cellHeight = (float) size.y / rows;

        for(int ctr = 1; ctr < rows; ctr++)
        {
            canvas.drawLine(1, ctr * cellHeight, size.x, ctr * cellHeight, paint);
        }

        for(int ctrTwo = 1; ctrTwo < cols; ctrTwo ++)
        {
            canvas.drawLine(ctrTwo * cellWidth, 1, ctrTwo * cellWidth, size.y, paint);
        }

        return bitmap;
    }

    /**
     * drawRectangles, draws the rectangles fro the grid
     * @param canvas
     */
    public synchronized void drawRectangles(Canvas canvas)
    {
        canvas.drawBitmap(grid, 0, 0, null);

        for(int row = 0; row < rows; row++)
        {
            for(int col = 0; col < cols; col++)
            {
                if(model.getCells()[row][col])
                {
                    //LEFT TOP RIGHT BOTTOM
                    canvas.drawRect(row * cellWidth, col * cellHeight,
                            row * cellWidth + cellWidth,
                            col * cellHeight + cellHeight, paintRect);
                }
            }
        }
    }

    /**
     * addCell, calls to add a single cell and sets the atomic bool
     * @param x
     * @param y
     */
    public void addCell(float x, float y)
    {
        atomicBoolean.set(true);

        int cellCol = (int) Math.floor(y / cellHeight);
        int cellRow = (int) Math.floor(x / cellWidth);

        model.addSingleCell(cellRow, cellCol);
    }

    /**
     * addGlider, calls to add a single Glider
     * @param x
     * @param y
     */
    public void addGlider(float x, float y)
    {
        int cellCol = (int) Math.floor(y / cellHeight);
        int cellRow = (int) Math.floor(x / cellWidth);

        DisplayMetrics metrics = drawView.getMetrics();

        if(x < metrics.widthPixels / 2 && y < metrics.heightPixels / 2)
        {
            quad = 'A';
        }
        else if(x > metrics.widthPixels / 2 && y < metrics.heightPixels / 2)
        {
            quad = 'B';
        }
        else if(x < metrics.widthPixels / 2 && y > metrics.heightPixels / 2)
        {
            quad = 'C';
        }
        else if(x > metrics.widthPixels / 2 && y > metrics.heightPixels / 2)
        {
            quad = 'D';
        }

        model.addGlider(cellRow, cellCol, quad);
    }

    /**
     * FPSThrottle, governs the FPS of the application
     * @throws InterruptedException
     */
    private void FPSThrottle() throws InterruptedException
    {
        long currentTime = System.currentTimeMillis();

        if (lastUpdated > 0)
        {
            long compare = currentTime - lastUpdated;

            if (compare < MIN_TIME)
            {
                drawThread.sleep(MIN_TIME - compare);
            }
        }

        lastUpdated = System.currentTimeMillis();
    }

    /**
     * setRunningThread, sets the runningThread
     * @param runningThread
     */
    public void setRunningThread(boolean runningThread) {
        this.runningThread = runningThread;
    }

    /**
     * setPaintRect, sets the colour for the rectangles
     * @param newPaint
     */
    public synchronized void setPaintRect(int newPaint)
    {
        this.paintRect.setColor(newPaint);
    }
}

