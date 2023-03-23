package com.jobis.controller;

import com.jobis.domain.dto.LoginRequestDto;
import com.jobis.domain.dto.SignUpRequestDto;
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
    private final ScrapService scrapService;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello2")
    public String hello2(){
        return "hello";
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignUpRequestDto memberRegistrationDTO) throws Exception {
        return ResponseEntity.ok().body(memberService.join(memberRegistrationDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto memberLoginDto){
        return ResponseEntity.ok().body(memberService.login(memberLoginDto));
    }

    @GetMapping("/me")
    public ResponseEntity<?> myInfo() throws Exception {
        return ResponseEntity.ok().body(memberService.getMemberInfo());
    }

    @PostMapping("/scrap")
    public ResponseEntity<?> scrap() throws Exception {
        return ResponseEntity.ok().body(scrapService.scrap());
    }

}
