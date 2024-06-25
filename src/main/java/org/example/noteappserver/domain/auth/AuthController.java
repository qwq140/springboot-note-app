package org.example.noteappserver.domain.auth;

import lombok.RequiredArgsConstructor;
import org.example.noteappserver.domain.auth.dto.*;
import org.example.noteappserver.domain.auth.service.AuthService;
import org.example.noteappserver.global.common.dto.CommonResponseDTO;
import org.example.noteappserver.global.common.dto.TokenResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinRequestDTO requestDTO) {
        authService.join(requestDTO);
        return new ResponseEntity<>(new CommonResponseDTO<>(1, "회원가입 성공"), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO requestDTO) {
        TokenResponseDTO responseDTO = authService.login(requestDTO);
        return new ResponseEntity<>(new CommonResponseDTO<>(1, "로그인 성공", responseDTO), HttpStatus.OK);
    }

    @PostMapping("/renewal")
    public ResponseEntity<?> renewal(@RequestBody RenewalRequestDTO requestDTO) {
        TokenResponseDTO responseDTO = authService.renewal(requestDTO);
        return new ResponseEntity<>(new CommonResponseDTO<>(1, "토큰 갱신", responseDTO), HttpStatus.OK);
    }


}
