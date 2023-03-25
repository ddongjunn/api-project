package com.jobis;


import com.jobis.domain.dto.SignUpRequestDto;

import com.jobis.service.MemberService;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@RestClientTest
public class JobisTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    /**
     * 회원가입
     */
    @Test
    public void singup(){
        SignUpRequestDto member = SignUpRequestDto.builder()
                .userId("hong")
                .password("1234")
                .name("홍길동")
                .regNo("860824-1655068")
                .build();


    }
}
