package ca.on.sl.comp208.lab1a;

import android.text.format.DateUtils;

import java.util.Date;

/**
 * Created by Ray on 2017-01-28.
 */

/**
 * Class made to convert the timer time and display it correctly
 */
public class StopWatch {

    private Date startTime;

    public void startTiming()
    {
        startTime = new Date();
    }

    public String stopTiming()
    {
        Date stopTime = new Date();
        long timediff = (stopTime.getTime() - startTime.getTime())/1000L;
        return(DateUtils.formatElapsedTime(timediff));
    }

}

