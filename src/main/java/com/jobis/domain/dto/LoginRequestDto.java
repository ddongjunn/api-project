package com.jobis.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Schema(name = "LoginRequestDto", description = "로그인 요청")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {


    @Schema(description = "아이디",  example = "hong")
    @NotBlank
    private String userId;

    @Schema(description = "비밀번호", example = "1234")
    @NotBlank
    private String password;
}
