package com.jobis.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_Id")
    private Member member;

    private String incomeDetail; //소득내역

    private Long totalPayment; //총지급액

    private LocalDateTime workStartDate; //업무시작일

    private String companyName; //기업명

    private String employeeName; //이름

    private LocalDateTime paymentDate; //지급일

    private LocalDateTime workEndDate; //업무종료일

    private String regNo; //주민등록번호

    private String incomeType; //소득구분

    private String businessRegistrationNumber; //사업자등록번호

    private String calculatedAmount; //산출새엑
}