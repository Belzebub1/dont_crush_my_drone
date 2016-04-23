package com.spaceapps.drone.service;

import com.spaceapps.drone.data.Drone;

/**
 * Created by Karen on 22-Apr-16.
 */
public interface DroneCallback {
    void droneServiceSuccess(Drone drone);
    void droneServiceFailure(Exception exception);
}
