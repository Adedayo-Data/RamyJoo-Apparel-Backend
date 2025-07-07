package com.ramyjoo.fashionstore.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConstant {

    @Value("${secret-key}")
    private String secretKey;

    public String getSecretKey() {
        return secretKey;
    }

    public static final String GET_HEADER = "Authorization";
}


