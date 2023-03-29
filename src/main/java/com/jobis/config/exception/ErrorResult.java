package com.jobis.config.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "에러 응답 메세지")
@Data
@AllArgsConstructor
public class ErrorResult {

    private String code;

    private String message;
}
