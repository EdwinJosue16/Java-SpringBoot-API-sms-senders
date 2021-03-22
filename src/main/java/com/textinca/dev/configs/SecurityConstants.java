package com.textinca.dev.configs;

public class SecurityConstants {
	// CONSTANTS FOR SECURITY AND JWT
    public static final String SECRET = "SuperStrongPassword"; // change in the future
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String URL_TRANSACTIONAL_BACKEND = "http://localhost:8080";
    public static final String ALL_END_POINTS = "/**";
    public static final String AUTHORIZATION_TYPE = "Authorization";
    public static final long JWT_TOKEN_VALIDITY = 604_800_000; // 7 days in milliseconds
}
