package com.spaceapps.drone.service;

import com.spaceapps.drone.data.Channel;

/**
 * Created by Karen on 22-Apr-16.
 */
public interface WeatherServiceCallback {
    void weatherServiceSuccess(Channel channel);
    void weatherServiceFailure(Exception exception);
}
