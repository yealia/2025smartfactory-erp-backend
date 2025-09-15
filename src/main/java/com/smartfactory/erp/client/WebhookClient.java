package com.smartfactory.erp.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WebhookClient {
    private final RestTemplate restTemplate;

    public WebhookClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /*
    targetUrl : 전송할 url
    payload : 전송할 DTO (어떤 타입이든지 재활용 가능하게)
     */
    public void send(String targetUrl, Object payload) {
        try {
            restTemplate.postForEntity(targetUrl, payload, String.class);
            System.out.println("Webhook sent to " + targetUrl + " with payload: " + payload);
        } catch (Exception e) {
            System.err.println("Failed to send webhook to " + targetUrl + ": " + e.getMessage());
        }
    }
}
