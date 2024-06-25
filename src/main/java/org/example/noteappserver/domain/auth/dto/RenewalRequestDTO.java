package org.example.noteappserver.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RenewalRequestDTO {

    private String refreshToken;
}
