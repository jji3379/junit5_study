package com.example.junit5_study.member;

import com.example.junit5_study.domain.Member;
import com.example.junit5_study.domain.Study;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long memberId);

    void validate(Long memberId);

    void notify(Study newstudy);

    void notify(Member member);
}
