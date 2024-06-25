package org.example.noteappserver.model.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Member {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
