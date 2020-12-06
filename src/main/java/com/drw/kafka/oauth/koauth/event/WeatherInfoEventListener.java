package com.drw.kafka.oauth.koauth.event;

public interface WeatherInfoEventListener {

    void onData(WeatherInfoEvent event);
    void processComplete();
}


