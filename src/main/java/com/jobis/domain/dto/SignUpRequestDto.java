package com.jobis.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Schema(description = "회원가입 요청")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequestDto {

    @Schema(description = "아이디", example = "hong")
    @NotBlank(message = "아이디를 입력해주세요.")
    private String userId;

    @Schema(description = "비밀번호", example = "1234")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @Schema(description = "이름", example = "홍길동")
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @Schema(description = "주민등록번호", example = "860824-1655068")
    @NotBlank(message = "주민등록번호를 입력해주세요.")
    @Pattern(regexp = "\\d{6}-[1-4]\\d{6}", message = "주민등록번호를 확인해주세요.")
    private String regNo;

}
