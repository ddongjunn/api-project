package com.jobis.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ScrapResponseSalaryDto {

    @JsonProperty("소득내역")
    private String incomeDetail; //소득내역

    @JsonProperty("총지급액")
    private String totalPayment; //총지급액

    @JsonProperty("업무시작일")
    private String workStartDate; //업무시작일

    @JsonProperty("기업명")
    private String companyName; //기업명

    @JsonProperty("이름")
    private String employeeName; //이름

    @JsonProperty("지급일")
    private String paymentDate; //지급일

    @JsonProperty("업무종료일")
    private String workEndDate; //업무종료일

    @JsonProperty("주민등록번호")
    private String regNo; //주민등록번호

    @JsonProperty("소득구분")
    private String incomeType; //소득구분

    @JsonProperty("사업자등록번호")
    private String businessRegistrationNumber; //사업자등록번호

    private String calculatedTaxAmount; //산출새엑
}
