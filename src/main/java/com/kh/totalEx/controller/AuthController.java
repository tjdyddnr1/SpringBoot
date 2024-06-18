package com.kh.totalEx.controller;

import com.kh.totalEx.DTO.MemberReqDto;
import com.kh.totalEx.DTO.MemberResDTO;
import com.kh.totalEx.DTO.TokenDTO;
import com.kh.totalEx.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResDTO> signup(@RequestBody MemberReqDto requestDto) {
        return ResponseEntity.ok(authService.signup(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody MemberReqDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }
    @PostMapping("/python")
    public String pythonInput(@RequestBody Map<String, String> data) {
        String id = data.get("id");
        String pwd = data.get("pwd");

        System.out.println("ID : " + id);
        System.out.println("PWD : " + pwd);
        return "ID : " + id + ", PWD : " + pwd;
    }


}