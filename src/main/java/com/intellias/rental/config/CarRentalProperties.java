package com.intellias.rental.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "email-sender")
public class CarRentalProperties {
    private String host;
    private Endpoint endpoint;
    private String fromEmail;

    @Data
    public static class Endpoint {
        private String confirmation;
    }
}
