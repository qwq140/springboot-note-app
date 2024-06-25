package org.example.noteappserver.domain.auth.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.example.noteappserver.domain.auth.dto.*;
import org.example.noteappserver.global.common.dto.TokenResponseDTO;
import org.example.noteappserver.global.exception.Exception400;
import org.example.noteappserver.global.security.JwtProvider;
import org.example.noteappserver.global.security.JwtType;
import org.example.noteappserver.model.member.Member;
import org.example.noteappserver.model.member.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public void join(JoinRequestDTO requestDTO) {

        requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        Member member = Member.builder()
                .username(requestDTO.getUsername())
                .password(requestDTO.getPassword())
                .nickname(requestDTO.getNickname())
                .role("USER")
                .build();

        memberRepository.save(member);
    }

    public TokenResponseDTO login(LoginRequestDTO requestDTO) {
        Member member = memberRepository.findByUsername(requestDTO.getUsername()).orElseThrow(
                () -> new Exception400("아이디 혹은 비밀번호가 틀렸습니다.")
        );

        if(!passwordEncoder.matches(requestDTO.getPassword(), member.getPassword())) {
            throw new Exception400("아이디 혹은 비밀번호가 틀렸습니다.");
        }

        String accessToken = jwtProvider.create(member, JwtType.ACCESS_TOKEN);
        String refreshToken = jwtProvider.create(member, JwtType.REFRESH_TOKEN);

        return new TokenResponseDTO(accessToken, refreshToken);
    }

    public TokenResponseDTO renewal(RenewalRequestDTO requestDTO) {
        // 디코딩
        DecodedJWT decodedJWT = jwtProvider.verify(requestDTO.getRefreshToken());

        // 토큰 타입 검증
        if(!decodedJWT.getClaim("token-type").asString().equals(JwtType.REFRESH_TOKEN.name())){
            throw new Exception400("refresh token이 아닙니다.");
        }

        Member member = memberRepository.findById(Long.parseLong(decodedJWT.getClaim("id").asString())).orElseThrow(
                () -> new Exception400("유효하지 않은 토큰입니다.")
        );

        String accessToken = jwtProvider.create(member, JwtType.ACCESS_TOKEN);
        String refreshToken = jwtProvider.create(member, JwtType.REFRESH_TOKEN);

        return new TokenResponseDTO(accessToken, refreshToken);
    }


}
