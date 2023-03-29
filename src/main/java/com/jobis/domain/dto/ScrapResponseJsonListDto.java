package com.jobis.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ScrapResponseJsonListDto {
    @JsonProperty("급여")
    private List<ScrapResponseSalaryDto> salary;

    @JsonProperty("산출세액")
    private String calculatedTaxAmount;

    @JsonProperty("소득공제")
    private List<ScrapResponseIncomeDeduction> incomeDeduction;
}
