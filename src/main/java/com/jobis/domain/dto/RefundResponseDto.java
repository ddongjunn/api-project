package com.jobis.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.NumberFormat;


@Schema(name = "RefundResponseDto", description = "스크랩 정보를 바탕으로 유저의 결정세액과 퇴직연금세액공제금액 결과를 응답해주는 DTO")
@Data
public class RefundResponseDto {

    @Schema(description = "이름", defaultValue = "", allowableValues = {}, example = "홍길동")
    @JsonProperty("이름")
    String name;

    @Schema(description = "결정세액", defaultValue = "", allowableValues = {}, example = "150,000")
    @JsonProperty("결정세액")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    String determinedTaxAmount;

    @Schema(description = "퇴직연금세액공제금액", defaultValue = "", allowableValues = {}, example = "900,000")
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
