package org.example.noteappserver.global.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenResponseDTO {
    private String accessToken;
    private String refreshToken;

    public TokenResponseDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
