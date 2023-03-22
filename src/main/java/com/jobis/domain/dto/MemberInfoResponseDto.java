package com.jobis.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberInfoResponseDto {

    private String userId;
    private String name;
    private String birthdate;
}
