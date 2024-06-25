package org.example.noteappserver.global.security;

public interface JwtVO {
    public static final String SECRET = "SECRET";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";
    public static final Long ACCESS_EXP = 1000L * 60 * 60 * 3; // 3시간
    public static final Long REFRESH_EXP = 1000L * 60 * 60 * 72; // 3일
}
