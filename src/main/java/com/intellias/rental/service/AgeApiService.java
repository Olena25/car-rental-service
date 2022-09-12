package com.intellias.rental.service;

import com.intellias.rental.dto.AgeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
@Slf4j

public class AgeApiService {

    private static final String URL_SCHEME = "https";

    @Value("${car-rental.agify-url}")
    private String url;
    private final RestTemplate restTemplate;

    public AgeResponse getUserAge(String name) {
        log.info("Trying to get age from agify for user with name {}", name);

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(URL_SCHEME)
                .host(url)
                .queryParam("name", name)
                .build();

        AgeResponse ageResponse = restTemplate.getForObject(uriComponents.toString(), AgeResponse.class);

        log.info("Received response from agify {}", ageResponse);

        return ageResponse;
    }
}
