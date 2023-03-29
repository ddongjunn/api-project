package com.jobis.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Schema(description = "회원가입 요청")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequestDto {

    @Schema(description = "아이디", example = "hong")
    @NotBlank
    private String userId;

    @Schema(description = "비밀번호", example = "1234")
    @NotBlank
    private String password;

    @Schema(description = "이름", example = "홍길동")
    @NotBlank
    private String name;

    @Schema(description = "주민등록번호", example = "860824-1655068")
    @NotBlank
    private String regNo;

}
