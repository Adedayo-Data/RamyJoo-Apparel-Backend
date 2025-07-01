package com.ramyjoo.fashionstore.service;

import com.ramyjoo.fashionstore.model.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaystackService {

    @Value("${paystack.secret-key}")
    private String secretKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public Object initializeTransaction(PaymentRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + secretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("email", request.getEmail());
        body.put("amount", request.getAmount());
        body.put("callback_url", "https://ramyjooapparel.com/payment-success");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        String url = "https://api.paystack.co/transaction/initialize";
        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        return response.getBody();
    }

    public Map<String, Object> verifyTransaction(String reference) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + secretKey);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String url = "https://api.paystack.co/transaction/verify/" + reference;
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        Map<String, Object> body = response.getBody();

        if (response.getStatusCode() == HttpStatus.OK &&
                body != null &&
                ((Map<?, ?>) body.get("data")).get("status").equals("success")) {

            return Map.of(
                    "status", true,
                    "message", "Payment verified",
                    "data", body.get("data")
            );
        }

        return Map.of(
                "status", false,
                "message", "Payment verification failed"
        );
    }
}

