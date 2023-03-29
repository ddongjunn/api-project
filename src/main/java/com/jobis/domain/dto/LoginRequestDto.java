package com.jobis.domain.dto;

<<<<<<< HEAD
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
=======
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
>>>>>>> 3f7faa693bab81d752a5d73620b743a73b93943f

import javax.validation.constraints.NotBlank;

@Schema(name = "LoginRequestDto", description = "로그인 요청")
@Getter
@Builder
@AllArgsConstructor
<<<<<<< HEAD
@NoArgsConstructor(access = AccessLevel.PROTECTED)
=======
@NoArgsConstructor
>>>>>>> 3f7faa693bab81d752a5d73620b743a73b93943f
public class LoginRequestDto {


    @Schema(description = "아이디",  example = "hong")
    @NotBlank
    private String userId;

    @Schema(description = "비밀번호", example = "1234")
    @NotBlank
    private String password;
}
