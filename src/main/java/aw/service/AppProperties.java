package aw.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("app")
public class AppProperties {
    private final String baseUrl;
}
