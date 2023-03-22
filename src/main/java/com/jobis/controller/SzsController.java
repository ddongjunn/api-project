package com.jobis.controller;

import com.jobis.domain.dto.MemberInfoResponseDto;
import com.jobis.domain.dto.MemberLoginDto;
import com.jobis.domain.dto.MemberRegistrationDto;
import com.jobis.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/szs")
@RequiredArgsConstructor
@Slf4j
public class SzsController {

    private final MemberService memberService;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello2")
    public String hello2(){
        return "hello";
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid MemberRegistrationDto memberRegistrationDTO){
        return ResponseEntity.ok(memberService.join(memberRegistrationDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginDto memberLoginDto){
        return ResponseEntity.ok(memberService.login(memberLoginDto));
    }

    @GetMapping("/me")
    public ResponseEntity<?> myInfo(){
        return ResponseEntity.ok(memberService.getMemberInfo());
    }

}
