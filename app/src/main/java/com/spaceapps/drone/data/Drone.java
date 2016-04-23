package com.spaceapps.drone.data;

import org.json.JSONObject;

/**
 * Created by Karen on 22-Apr-16.
 */
public class Drone implements JSONPopulator {

    private double latitude;
    private double longitude;
    private double heading;

    @Override
    public void populate(JSONObject data) {
        latitude = data.optDouble("lat");
        longitude = data.optDouble("lon");
        heading = data.optDouble("heading");
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getHeading() {
        return heading;
    }
}
