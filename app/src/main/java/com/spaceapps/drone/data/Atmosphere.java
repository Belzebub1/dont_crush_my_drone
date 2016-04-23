package com.spaceapps.drone.data;

import org.json.JSONObject;

/**
 * Created by Karen on 22-Apr-16.
 */
public class Atmosphere implements JSONPopulator {

    private int visibility;
    @Override
    public void populate(JSONObject data) {
        visibility = data.optInt("visibility");
    }

    public int getVisibility() {
        return visibility;
    }
}
