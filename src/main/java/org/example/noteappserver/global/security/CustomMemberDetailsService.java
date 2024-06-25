package org.example.noteappserver.global.security;

import lombok.RequiredArgsConstructor;
import org.example.noteappserver.global.exception.Exception401;
import org.example.noteappserver.model.member.Member;
import org.example.noteappserver.model.member.MemberRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public CustomMemberDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new Exception401("아이디를 찾을 수 없습니다."));
        return new CustomMemberDetails(member);
    }
}
