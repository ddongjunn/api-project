package com.jobis.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class SignUpResponseDto {

    private String userId;
    private LocalDateTime joinDate;
}
