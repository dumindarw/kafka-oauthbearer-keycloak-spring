package com.drw.kafka.oauth.koauth.consumer;

import com.drw.kafka.oauth.koauth.event.WeatherInfoEvent;
import com.drw.kafka.oauth.koauth.event.WeatherInfoEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class WeatherInfoEventProcessor {

    private WeatherInfoEventListener listener;

    public void register(WeatherInfoEventListener listener) {
        this.listener = listener;
    }

    public void onEvent(WeatherInfoEvent event) {
        if (listener != null) {
            listener.onData(event);
        }
    }

    public void onComplete() {
        if (listener != null) {
            listener.processComplete();
        }
    }

    @KafkaListener(topics = "weather", groupId = "group_id")
    public void consume(WeatherInfoEvent message) throws IOException {
        log.info(String.format("#### -> Consumed message -> %s", message));
        onEvent(message);
    }
}
