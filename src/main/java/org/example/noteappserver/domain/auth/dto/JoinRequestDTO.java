package org.example.noteappserver.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequestDTO {
    private String username;
    private String password;
    private String nickname;
}
