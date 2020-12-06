package com.drw.kafka.oauth.koauth.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeatherInfoEvent {

    private int stationId;
    private int temperature;


}
