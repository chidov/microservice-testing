package com.egen.foodclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * @author cdov
 */
@Component
public class FoodClient {
    private final RestTemplate restTemplate;

    public FoodClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${server.base-url:http://localhost:8080}")
    private String baseUrl;

    public List<Food> getFoods(){
        Food[] foods =restTemplate.getForObject(baseUrl.concat("/foods"), Food[].class);
        return Arrays.asList(foods);
    }
}
