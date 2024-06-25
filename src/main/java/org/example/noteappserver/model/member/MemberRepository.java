package org.example.noteappserver.model.member;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface MemberRepository {
    Optional<Member> findByUsername(String username);
    Optional<Member> findById(Long id);
    Optional<Member> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    void save(Member member);
}
