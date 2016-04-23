package com.spaceapps.drone.data;

import org.json.JSONObject;

/**
 * Created by Karen on 22-Apr-16.
 */
public class Wind implements JSONPopulator {

    private int speed;

    @Override
    public void populate(JSONObject data) {
        speed = data.optInt("speed");
    }

    public int getSpeed() {
        return speed;
    }
}
