package com.jobis.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.NumberFormat;

@Data
public class RefundResponseDto {

    @JsonProperty("이름")
    String name;

    @JsonProperty("결정세액")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    String determinedTaxAmount;

    @JsonProperty("퇴직연금세액공제금액")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "###,###")
    String retirementPensionTaxDeduction;

    public RefundResponseDto(String name, Long determinedTaxAmount, Long retirementPensionTaxDeduction) {
        this.name = name;
        this.determinedTaxAmount = numberFormat(determinedTaxAmount);
        this.retirementPensionTaxDeduction = numberFormat(retirementPensionTaxDeduction);
    }

    public String numberFormat(long number){
        return NumberFormat.getNumberInstance().format(number);
    }
}
