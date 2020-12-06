package com.drw.kafka.oauth.koauth.controller;

import com.drw.kafka.oauth.koauth.consumer.WeatherInfoEventProcessor;
import com.drw.kafka.oauth.koauth.event.WeatherInfoEvent;
import com.drw.kafka.oauth.koauth.event.WeatherInfoEventListener;
import com.drw.kafka.oauth.koauth.producer.WeatherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;

@RestController  // (1) Spring MVC annotation
public class WeatherInfoController {

    @Autowired
    WeatherInfoService service;

    @Autowired
    private WeatherInfoEventProcessor processor;

    private Flux<WeatherInfoEvent> bridge;



    public WeatherInfoController() {
        // (3) Broadcast to several subscribers
        this.bridge = createBridge().publish().autoConnect().cache(10).log();
    }

    @PostMapping("/weather")
    public void generateWeather(){
        try {
            service.getWeatherInfoJob();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // (1) Spring MVC annotation
    @GetMapping(value = "/weather", produces = "text/event-stream;charset=UTF-8")
    public Flux<WeatherInfoEvent> getWeatherInfo() {
        return bridge;
    }

    private Flux<WeatherInfoEvent> createBridge() {
        Flux<WeatherInfoEvent> bridge = Flux.create(sink -> { // (2)
            processor.register(new WeatherInfoEventListener() {

                @Override
                public void processComplete() {
                    sink.complete();
                }

                @Override
                public void onData(WeatherInfoEvent data) {
                    sink.next(data);
                }
            });
        });
        return bridge;
    }
}
