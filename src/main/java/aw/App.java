package aw;

import aw.service.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.event.EventListener;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
@ConfigurationPropertiesScan
public class App {

    private final AppProperties appProperties;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void welcome() {
        log.info("Open {}", appProperties.getBaseUrl());
    }
}
