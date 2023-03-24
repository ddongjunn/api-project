package com.jobis.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ScrapResponseIncomeDeduction {

    @JsonProperty("금액")
    @JsonAlias("총납임금액")
    private String amount;

    @JsonProperty("소득구분")
    private String incomeType;

}
