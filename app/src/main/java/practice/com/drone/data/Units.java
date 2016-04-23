package practice.com.drone.data;

import org.json.JSONObject;

/**
 * Created by Karen on 22-Apr-16.
 */
public class Units implements JSONPopulator {

    private String temperature;

    @Override
    public void populate(JSONObject data) {
        temperature = data.optString("temperature");
    }

    public String getTemperature() {
        return temperature;
    }
}
