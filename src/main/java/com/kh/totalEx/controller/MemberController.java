package com.kh.totalEx.controller;

import com.kh.totalEx.DTO.MemberResDTO;
import com.kh.totalEx.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    // 회원 전체 조회
    @GetMapping("/list")
    public ResponseEntity<List<MemberResDTO>> memberList() {
        List<MemberResDTO> list = memberService.getMemberList();
        return ResponseEntity.ok(list);
    }
}