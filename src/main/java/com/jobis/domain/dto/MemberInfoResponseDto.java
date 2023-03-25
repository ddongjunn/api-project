package com.jobis.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberInfoResponseDto {

    @JsonProperty("아이디")
    private String userId;

    @JsonProperty("이름")
    private String name;

    @JsonProperty("생년월일")
    private String birthdate;
}
