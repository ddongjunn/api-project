package com.jobis.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(name = "LoginResponseDto", description = "로그인 응답")
@Getter
@Setter
@RequiredArgsConstructor
public class LoginResponseDto {

    @Schema(description = "토큰", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJraW0iLCJleHAiOjE2Nzk4NDUyMTQsImlhdCI6MTY3OTgwOTIxNH0.iG0X9JB_FunwRhGxd6ruaZv_y3_cJvKIe7Gv2BNDEMI22igci2pnEIGNZQC9BwRyJd133IJHbHLCSiGSCLVm2g")
    private final String accessToken;
}
