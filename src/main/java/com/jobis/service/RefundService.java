package com.jobis.service;

import com.jobis.common.utils.TokenProvider;
import com.jobis.config.exception.NoSuchSalaryDataException;
import com.jobis.domain.SalaryMapping;
import com.jobis.domain.dto.RefundResponseDto;
import com.jobis.repository.IncomeDeductionRepository;
import com.jobis.repository.SalaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefundService {

    private final IncomeDeductionRepository incomeDeductionRepository;
    private final SalaryRepository salaryRepository;
    private final TokenProvider tokenProvider;
    public RefundResponseDto refund(HttpServletRequest request){
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        String userId = tokenProvider.validateTokenAndGetUsername(token);

        //산출세액, 총급여
        List<SalaryMapping> salaryData = salaryRepository.findByMemberUserId(userId);
        Long calculatedTaxAmount = 0L;
        Long totalPayment = 0L;
        try {
            calculatedTaxAmount = salaryData.get(0).getCalculatedTaxAmount();
            totalPayment = salaryData.get(0).getTotalPayment();
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchSalaryDataException("스크랩 데이터를 찾을 수 없습니다.");
        }

        Long employmentIncomeTaxAmount = calculateEmploymentIncomeTaxAmount(calculatedTaxAmount);

        List<Map<String, Object>> incomeDeductionData = incomeDeductionRepository.findByUserId(userId);
        Long retirementPensionTaxAmount = calculateRetirementPensionTaxAmount(incomeDeductionData);
        Long specialDeductionTaxAmount = getSpecialDeductionTaxAmount(incomeDeductionData, totalPayment);
        Long standardTaxAmount = calculateStandardTaxAmount(specialDeductionTaxAmount);

        Long determinedTaxAmount = calculateDeterminedTaxAmount(calculatedTaxAmount, employmentIncomeTaxAmount, specialDeductionTaxAmount, standardTaxAmount, retirementPensionTaxAmount);

        return new RefundResponseDto(userId, determinedTaxAmount, retirementPensionTaxAmount);
    }

    /**
     * 표준세액공제금액
     */
    private Long calculateDeterminedTaxAmount(Long calculatedTaxAmount, Long employmentIncomeTaxAmount, Long specialDeductionTaxAmount, Long standardTaxAmount, Long retirementPensionTaxAmount) {
        long determinedTaxAmount = calculatedTaxAmount - employmentIncomeTaxAmount - specialDeductionTaxAmount - standardTaxAmount - retirementPensionTaxAmount;
        if(determinedTaxAmount < 0){
            determinedTaxAmount = 0L;
        }
        return determinedTaxAmount;
    }


    /**
     * 근로소득세액공제금액
     */
    public Long calculateEmploymentIncomeTaxAmount(Long employmentIncomeTaxCredit){
        return (long) (employmentIncomeTaxCredit * 0.55);
    }


    /**
     * 퇴직연금세액공제금액
     */
    public Long calculateRetirementPensionTaxAmount(List<Map<String, Object>> incomeDeductionData){
        Long retirementPensionPaymentAmount = null;

        for (Map<String, Object> map : incomeDeductionData) {
            if(map.get("incomeType").equals("퇴직연금")){
                retirementPensionPaymentAmount = (Long) map.get("amount");
            }
        }

        if(retirementPensionPaymentAmount != null){
            return (long) (retirementPensionPaymentAmount * 0.15);
        }else{
           throw new NoSuchElementException("퇴직연금 데이터가 존재하지 않습니다.");
        }
    }

    /**
     * 특별세액공제금액 (보혐료공제금액, 의료비공제금액, 교육비공제금액, 기부금공제금액)
     */
    public Long getSpecialDeductionTaxAmount(List<Map<String, Object>> incomeDeductionData, Long totalPayment){
        Long specialDeductionTaxAmount = 0L;

        for (Map<String, Object> map : incomeDeductionData) {
            String incomeType = (String) map.get("incomeType");
            Long amount = (Long) map.get("amount");

            switch (incomeType) {
                case "보험료":
                    specialDeductionTaxAmount += calculateInsuranceDeductionAmount(amount);
                    log.info("보혐료={},보험료 공제금액 ={}",amount,calculateInsuranceDeductionAmount(amount));
                    break;
                case "의료비":
                    specialDeductionTaxAmount += calculateMedicalDeductionAmount(amount, totalPayment);
                    log.info("의료비={},의료비 공제금액={} ",amount,calculateMedicalDeductionAmount(amount,totalPayment));
                    break;
                case "교육비":
                    specialDeductionTaxAmount += calculateEducationDeductionAmount(amount);
                    log.info("교육비={}, 교육비 공제금액={} ",amount,calculateEducationDeductionAmount(amount));
                    break;
                case "기부금":
                    specialDeductionTaxAmount += calculateDonationDeductionAmount(amount);
                    log.info("기부금={}, 기부금 공제금액={} ",amount,calculateDonationDeductionAmount(amount));
                    break;
            }
        }
        return specialDeductionTaxAmount;
    }

    /**
     * 보험료공제금액 보험료납입금액 * 12%
     */
    public Long calculateInsuranceDeductionAmount(Long amount){
        return (long) (amount * 0.12);
    }

    /**
     * 의료비공제금액 (의료비 - 총급여 * 3%) * 15%
     */
    public Long calculateMedicalDeductionAmount(Long amount, Long totalPayment){
        long medicalDeductionAmount = (long) ((amount - (totalPayment * 0.03)) * 0.15);
        return medicalDeductionAmount < 0 ? 0L : medicalDeductionAmount;
    }

    /**
     * 교육비공제금액 (교육비 )
     */
    public Long calculateEducationDeductionAmount(Long amount){
        return (long) (amount * 0.15);
    }

    /**
     * 기부금공제금액
     */
    public Long calculateDonationDeductionAmount(Long amount){
        return (long) (amount * 0.15);
    }

    /**
     * 표준세액공제금액
     */
    public Long calculateStandardTaxAmount(Long specialDeductionTaxAmount){
        long standardTaxAmount = 0L;
        if(specialDeductionTaxAmount > 130001){
            standardTaxAmount = 130000;
        }
        return standardTaxAmount;
    }

}
