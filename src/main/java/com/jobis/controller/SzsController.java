package com.jobis.controller;

import com.jobis.domain.dto.MemberRegistrationDTO;
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

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid MemberRegistrationDTO memberRegistrationDTO){
        return ResponseEntity.ok(memberService.join(memberRegistrationDTO));
    }
}
