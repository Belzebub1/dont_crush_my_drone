package practice.com.drone.service;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import practice.com.drone.data.Drone;

/**
 * Created by Karen on 22-Apr-16.
 */
public class DroneService {
    private DroneCallback callback;
    private String id;
    private Exception error;

    public DroneService(DroneCallback callback) {
        this.callback = callback;
    }

    public void refreshLocation(String droneId){
        this.id=droneId;
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String endpoint = String.format("http://52.20.132.108:3000/getPos/%s", strings[0]);

                try {
                    URL url = new URL(endpoint);
                    URLConnection connection = url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        result.append(line);
                    }
                    return result.toString();
                } catch (Exception e) {
                    error = e;
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {

                if(s==null && error!=null){
                    callback.droneServiceFailure(error);
                    return;
                }

                try {
                    JSONObject data = new JSONObject(s);

                    Drone drone = new Drone();
                    drone.populate(data);
                    callback.droneServiceSuccess(drone);

                } catch (JSONException e) {
                    callback.droneServiceFailure(e);
                }
            }
        }.execute(droneId);
    }
}
