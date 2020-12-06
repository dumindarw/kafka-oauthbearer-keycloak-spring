package com.drw.kafka.oauth.koauth.producer;

import com.drw.kafka.oauth.koauth.event.WeatherInfoEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.apache.kafka.common.security.scram.internals.ScramMechanism.SCRAM_SHA_256;

@Service
@Slf4j
public class WeatherInfoService {

    private static final Random RANDOM = new Random();

    public static int random(int min, int max) {
        return RANDOM.nextInt(max) + min;
    }



    @Autowired
    private KafkaTemplate<String, WeatherInfoEvent> kafkaTemplate;

    public ListenableFuture<SendResult<String, WeatherInfoEvent>> sendMessage(String topic, WeatherInfoEvent message) {
        log.info(String.format("#### -> Producing message -> %s", message));
        return this.kafkaTemplate.send(topic, message);
    }

    @Scheduled(fixedDelay = 5000)
    public void getWeatherInfoJob() throws IOException {
        log.info("generate fake weather event");
        // fake event
        WeatherInfoEvent event = new WeatherInfoEvent(random(0, 100), random(16, 30));
        sendMessage("weather", event);
    }
}
