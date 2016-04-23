package practice.com.drone.service;

import practice.com.drone.data.Channel;

/**
 * Created by Karen on 22-Apr-16.
 */
public interface WeatherServiceCallback {
    void weatherServiceSuccess(Channel channel);
    void weatherServiceFailure(Exception exception);
}
