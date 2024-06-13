package com.kh.totalEx.service;

import com.kh.totalEx.DTO.MemberResDTO;
import com.kh.totalEx.entity.Member;
import com.kh.totalEx.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원 전체 조회
    public List<MemberResDTO> getMemberList() {
        List<Member> members = memberRepository.findAll();
        List<MemberResDTO> memberDtos = new ArrayList<>();
        for(Member member : members) {
            memberDtos.add(MemberResDTO.of(member));
        }
        return memberDtos;
    }
}