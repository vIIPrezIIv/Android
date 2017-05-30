package ca.on.sl.comp208.assignment3realortelli;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



/**
 * Created by Ray on 2017-04-06.
 */

public class NetworkUtls {

    /**
     * networkAvailable, Handles if the network is available to access
     * @param context
     * @return
     */
    public static boolean networkAvailable(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = cm.getActiveNetworkInfo();

        try
        {
            if(network != null)
            {
                return network.isConnectedOrConnecting();
            }
        }

        catch (Exception e)
        {
            Log.i("Network", e.getMessage());
        }

        return false;
    }

    /**
     * getNetworkData, Gets and executes the URI's for the API
     * @param address
     * @return
     */
    public static String getNetworkData(String address)
    {
        try
        {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(2000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            int responseCode = connection.getResponseCode();

            if(responseCode == 200)
            {
                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bReader = new BufferedReader(inputStreamReader);
                String line = bReader.readLine();
                return line;
            }
        }

        catch(MalformedURLException e)
        {
            Log.i("URL", e.getMessage());
        }
        catch(IOException e)
        {
            Log.i("URL", e.getMessage());
        }

        return null;
    }
}
