package org.example.noteappserver.global.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.example.noteappserver.global.exception.Exception401;
import org.example.noteappserver.model.member.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final Algorithm algorithm = Algorithm.HMAC512(JwtVO.SECRET);
    private final CustomMemberDetailsService customMemberDetailsService;

    public String create(Member member, JwtType jwtType) {

        String jwt = JWT.create()
                .withSubject("note")
                .withExpiresAt(createExpireDate(jwtType))
                .withClaim("id", member.getId())
                .withClaim("username", member.getUsername())
                .withClaim("token-type", jwtType.name())
                .sign(algorithm);

        return jwt;
    }

    public DecodedJWT verify(String jwt) {

        if(jwt.startsWith(JwtVO.TOKEN_PREFIX)) {
            jwt = jwt.substring(JwtVO.TOKEN_PREFIX.length());
        }

        return JWT.require(algorithm).build().verify(jwt);
    }

    public boolean validateToken(String jwt) {
        try {
            DecodedJWT verify = verify(jwt);

            if(new Date().after(verify.getExpiresAt())) {
                throw new Exception401("토큰이 만료되었습니다.");
            }

            if(!verify.getClaim("token-type").asString().equals(JwtType.ACCESS_TOKEN.name())) {
                throw new Exception401("올바르지 않은 토큰입니다.");
            }
        } catch (JWTVerificationException e) {
            return false;
        }

        return true;
    }

    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = verify(token);
        String username = decodedJWT.getClaim("username").asString();
        CustomMemberDetails memberDetails = customMemberDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(memberDetails, "", memberDetails.getAuthorities());
    }

    private Date createExpireDate(JwtType jwtType) {
        if(jwtType.equals(JwtType.ACCESS_TOKEN)){
            return new Date(System.currentTimeMillis() + JwtVO.ACCESS_EXP);
        } else if(jwtType.equals(JwtType.REFRESH_TOKEN)){
            return new Date(System.currentTimeMillis() + JwtVO.REFRESH_EXP);
        } else {
            throw new RuntimeException();
        }
    }



}
