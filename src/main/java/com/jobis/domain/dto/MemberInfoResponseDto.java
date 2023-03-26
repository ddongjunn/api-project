package com.jobis.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "MemberInfoResponseDto", description = "내 정보 조회 응답")
@Getter
@Setter
@AllArgsConstructor
public class MemberInfoResponseDto {

    @Schema(description = "아이디" ,example = "hong")
    @JsonProperty("아이디")
    private String userId;

    @Schema(description = "이름", example = "홍길동")
    @JsonProperty("이름")
    private String name;

    @Schema(description = "생년월일", example = "860824")
    @JsonProperty("생년월일")
    private String birthdate;
}
