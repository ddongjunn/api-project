package com.jobis.domain.dto;

import lombok.Data;


import java.util.Map;

@Data
public class ScrapResponseDto {

    private String status;
    private ScrapResponseDataDto data;
    private Map<String, Object> errors;
}
