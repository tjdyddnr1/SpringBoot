package com.kh.totalEx.repository;

import com.kh.totalEx.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")

class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원 insert")
    public void createMemberTest() {
        for (int i = 1; i <= 10; i++) {
            Member member = new Member();

            member.setName("회원"+i);
            member.setPwd("bbb"+i);
            member.setEmail("dyd@na"+i);
            member.setImage("1"+i);
            member.setRegDate(LocalDateTime.now());
            memberRepository.save(member);
        }
    }
    @Test
    @DisplayName("전체 회원 조회")
    public void findAll() {
        this.createMemberTest();
        List<Member> memberList = memberRepository.findAll();
        for(Member e : memberList) System.out.println(e.toString());
    }
    @Test
    @DisplayName("가입 여부 확인")
    public void findByEmail() {
        this.createMemberTest();
        Optional<Member> memberOptional = memberRepository.findByEmail("dyd@na5");
        System.out.println(memberOptional.isPresent());

    }
    @Test
    @DisplayName("로그인 여부")
    public void findByEmailAndPwd() {
        this.createMemberTest();
        Optional<Member> memberOptional = memberRepository.findByEmailAndPwd("dyd@na5", "bbb4");
        System.out.println(memberOptional.isPresent());
    }
}