package com.jobis.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class MemberRegistrationDTO {

    @NotBlank
    @ApiModelProperty(example = "test_id")
    private String userId;

    @NotBlank
    @ApiModelProperty(example = "15125912")
    private String password;

    @NotBlank
    @ApiModelProperty(example = "테스터")
    private String name;

    @NotBlank
    @ApiModelProperty(example = "930501-012921")
    private String regNo;

}
