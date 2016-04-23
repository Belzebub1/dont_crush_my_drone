package practice.com.drone.data;

import org.json.JSONObject;

/**
 * Created by Karen on 22-Apr-16.
 */
public class Location implements JSONPopulator {

    private String city;
    private String country;

    @Override
    public void populate(JSONObject data) {
        city = data.optString("city");
        country = data.optString("country");
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
