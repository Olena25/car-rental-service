package com.intellias.rental.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailApiService {

    @Value("${car-rental.email-service-url}")
    private String url;

    private final RestTemplate restTemplate;

    public void sendEmailToConfirm(int userId) {
        log.info("Sending confirmation email to user with id {}", userId);
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url)
                .pathSegment(String.valueOf(userId))
                .build();

        restTemplate.postForObject(uriComponents.toString(), null, Void.class);

        log.info("Confirmation email sent successfully for user with id {}", userId);
    }

}
