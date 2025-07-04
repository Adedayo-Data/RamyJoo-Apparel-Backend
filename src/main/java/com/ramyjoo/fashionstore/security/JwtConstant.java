package com.ramyjoo.fashionstore.security;

import org.springframework.beans.factory.annotation.Value;

public class JwtConstant {

    @Value("${secret-key}")
    public static String SECRET_KEY;

    public static final String GET_HEADER = "Authorization";

}

