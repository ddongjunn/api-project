package com.jobis.controller;

import com.jobis.domain.dto.LoginRequestDto;
import com.jobis.domain.dto.SignUpRequestDto;
import com.jobis.service.MemberService;
import com.jobis.service.ScrapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/szs")
@RequiredArgsConstructor
@Slf4j
public class SzsController {

    private final MemberService memberService;
    private final ScrapService scrapService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignUpRequestDto memberRegistrationDTO) {
        return ResponseEntity.ok().body(memberService.join(memberRegistrationDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto memberLoginDto){
        return ResponseEntity.ok().body(memberService.login(memberLoginDto));
    }

    @GetMapping("/me")
    public ResponseEntity<?> myInfo(HttpServletRequest request)  {
        return ResponseEntity.ok().body(memberService.getMemberInfo(request));
    }

    @PostMapping("/scrap")
    public ResponseEntity<?> scrap(HttpServletRequest request)  {
        return ResponseEntity.ok().body(scrapService.scrap(request));
    }

//    @GetMapping("/szs/refund")
//    public ResponseEntity<?> refund(){
//        return ResponseEntity.ok().body();
//    }

}
