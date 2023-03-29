package com.jobis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobis.common.utils.TokenProvider;
import com.jobis.domain.dto.LoginResponseDto;
import com.jobis.domain.dto.SignUpRequestDto;
import com.jobis.domain.dto.SignUpResponseDto;

import com.jobis.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SzsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @Test
    void signup() throws Exception {
        SignUpResponseDto signUpResponseDto = SignUpResponseDto.builder()
                .userId("hong")
                .joinDate(LocalDateTime.now())
                .build();

        given(memberService.join(any())).willReturn(signUpResponseDto);

        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .userId("hong")
                .password("1234")
                .name("홍길동")
                .regNo("860824-1655068")
                .build();
        String requestBody = objectMapper.writeValueAsString(signUpRequestDto);

        mockMvc.perform(post("/szs/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(signUpResponseDto.getUserId()));
    }

    @Test
    void login() throws Exception {
    }
}