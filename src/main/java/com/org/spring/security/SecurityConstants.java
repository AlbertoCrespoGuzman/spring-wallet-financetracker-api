package com.org.spring.security;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_TOKEN_STRING = "Authorization";
    public static final String HEADER_USER_ID_STRING = "User_id";
    public static final String SIGN_UP_URL = "/users/sign-up";
    public static final String SIGN_UP_CONFIRMATION_URL = "/users/registrationConfirm";
    public static final String RESET_PASSWORD_URL = "/users/resetPassword";
    public static final String CHANGE_PASSWORD_URL = "/users/changePassword";
    public static final String SAVE_PASSWORD_URL = "/users/savePassword";
    public static final String SIGN_IN_FACEBOOK_URL = "/users/signin/facebook";
}