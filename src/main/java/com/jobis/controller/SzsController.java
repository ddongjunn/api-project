package com.jobis.controller;

import com.jobis.config.exception.ErrorResult;
import com.jobis.domain.dto.*;
import com.jobis.service.MemberService;
<<<<<<< HEAD
import com.jobis.service.ScrapService;

=======
import com.jobis.service.RefundService;
import com.jobis.service.ScrapService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
>>>>>>> 3f7faa693bab81d752a5d73620b743a73b93943f
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
    private final RefundService refundService;

<<<<<<< HEAD
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello2")
    public String hello2() {
        return "hello";
    }

=======
    @Operation(summary = "회원가입",
    responses = {
            @ApiResponse(responseCode = "200", description = "ok",
                    content = @Content(schema = @Schema(implementation = SignUpResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "UserAlreadyExists",
                    content = @Content(schema = @Schema(implementation = ErrorResult.class))),
            @ApiResponse(responseCode = "401", description = "RegistrationNotAllowed",
                    content = @Content(schema = @Schema(implementation = ErrorResult.class)))
    })
>>>>>>> 3f7faa693bab81d752a5d73620b743a73b93943f
    @PostMapping("/signup")
    public ResponseEntity<?> signup(
            @RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        return ResponseEntity.ok().body(memberService.join(signUpRequestDto));
    }

    @Operation(summary = "로그인",
            responses = {
                    @ApiResponse(responseCode = "200", description = "ok",
                            content = @Content(schema = @Schema(implementation = LoginResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "BadCredentials",
                            content = @Content(schema = @Schema(implementation = ErrorResult.class)))
            })
    @PostMapping("/login")
<<<<<<< HEAD
    public ResponseEntity<?> login(@RequestBody LoginRequestDto memberLoginDto) {
        return ResponseEntity.ok().body(memberService.login(memberLoginDto));
=======
    public ResponseEntity<?> login(
            @Parameter(description = "로그인 요청")
            @RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok().body(memberService.login(loginRequestDto));
>>>>>>> 3f7faa693bab81d752a5d73620b743a73b93943f
    }

    @Operation(summary = "사용자 정보조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "ok",
                            content = @Content(schema = @Schema(implementation = MemberInfoResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "BadCredentials",
                            content = @Content(schema = @Schema(implementation = ErrorResult.class)))
            })
    @GetMapping("/me")
    public ResponseEntity<?> myInfo(HttpServletRequest request)  {
        return ResponseEntity.ok().body(memberService.getMemberInfo(request));
    }

    @Operation(summary = "스크랩 API 호출",
            responses = {
                    @ApiResponse(responseCode = "200", description = "ok",
                            content = @Content(schema = @Schema(implementation = ScrapResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ErrorResult.class)))
            })
    @PostMapping("/scrap")
    public ResponseEntity<?> scrap(HttpServletRequest request)  {
        return ResponseEntity.ok().body(scrapService.scrap(request));
    }

    @Operation(summary = "스크랩 API 데이터 바탕으로 결정세액, 퇴직연금세액공제금액 조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "ok",
                            content = @Content(schema = @Schema(implementation = RefundResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "NoSuchSalaryData",
                            content = @Content(schema = @Schema(implementation = ErrorResult.class)))
            })
    @GetMapping("/refund")
    public ResponseEntity<?> refund(HttpServletRequest request){
        return ResponseEntity.ok().body(refundService.refund(request));
    }

}
