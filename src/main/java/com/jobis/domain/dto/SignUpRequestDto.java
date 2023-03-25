package com.jobis.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SignUpRequestDto {

    @NotBlank
    private String userId;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String regNo;

}
