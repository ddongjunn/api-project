package com.jobis.service;

import com.jobis.common.utils.AES256;
import com.jobis.common.utils.TokenProvider;
import com.jobis.config.exception.UserNotFoundException;
import com.jobis.domain.dto.*;
import com.jobis.domain.entity.IncomeDeduction;
import com.jobis.domain.entity.Member;
import com.jobis.domain.entity.Salary;
import com.jobis.repository.IncomeDeductionRepository;
import com.jobis.repository.MemberRepository;
import com.jobis.repository.SalaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScrapService {

    private final MemberRepository memberRepository;
    private final SalaryRepository salaryRepository;
    private final IncomeDeductionRepository incomeDeductionRepository;
    private final TokenProvider tokenProvider;
    private final AES256 aes256;

    public ScrapResponse scrap(String token)  {
        String userId = tokenProvider.validateTokenAndGetUsername(token);
        log.info("token {} ",token);
        log.info("userId {} ",userId);
        Optional<Member> member = memberRepository.findByUserId(userId);
        Member loginMember = member.orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        ScrapResponseDto scrapResponseDto = callScrapApi(loginMember);
        saveSalaryEntityFromScrapApi(scrapResponseDto, loginMember);
        saveIncomeDeductionEntityFromScrapApi(scrapResponseDto, loginMember);

        return new ScrapResponse("success", "성공적으로 저장하였습니다.");
    }

    public ScrapResponseDto callScrapApi(Member loginMember) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://codetest.3o3.co.kr/v2/scrap")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        ScrapRequestDto scrapRequestDto = new ScrapRequestDto(loginMember.getName(), getDecryptedString(loginMember.getRegNo()));

        return  webClient
                .post()
                .bodyValue(scrapRequestDto)
                .retrieve()
                .bodyToMono(ScrapResponseDto.class)
                .doOnError(e -> log.error("call scrap api error = {}",e))
                .block();
    }

    public void saveSalaryEntityFromScrapApi(ScrapResponseDto scrapResponseDto, Member member) {
        ScrapResponseSalaryDto salaryDto = scrapResponseDto.getData().getJsonList().getSalary().get(0);
        Salary salary = Salary.builder()
                .member(member)
                .incomeDetail(salaryDto.getIncomeDetail())
                .totalPayment(commaSeparatedStringToLong(salaryDto.getTotalPayment()))
                .workStartDate(convertToLocalDate(salaryDto.getWorkStartDate()))
                .companyName(salaryDto.getCompanyName())
                .employeeName(salaryDto.getEmployeeName())
                .paymentDate(convertToLocalDate(salaryDto.getPaymentDate()))
                .workEndDate(convertToLocalDate(salaryDto.getWorkEndDate()))
                .regNo(encryptAES256String(salaryDto.getRegNo()))
                .incomeType(salaryDto.getIncomeType())
                .businessRegistrationNumber(salaryDto.getBusinessRegistrationNumber())
                .calculatedTaxAmount(commaSeparatedStringToLong(scrapResponseDto.getData().getJsonList().getCalculatedTaxAmount()))
                .build();
        salaryRepository.save(salary);
    }

    public void saveIncomeDeductionEntityFromScrapApi(ScrapResponseDto scrapResponseDto, Member member){
        List<ScrapResponseIncomeDeduction> incomeDeductionDto = scrapResponseDto.getData().getJsonList().getIncomeDeduction();
        incomeDeductionDto.forEach(e -> {
            IncomeDeduction incomeDeduction = IncomeDeduction.builder()
                    .member(member)
                    .incomeType(e.getIncomeType())
                    .amount(commaSeparatedStringToLong(e.getAmount()))
                    .build();
            incomeDeductionRepository.save(incomeDeduction);
        });
    }

    public Long commaSeparatedStringToLong(String str){
        str = str.replaceAll(",","");
        String[] parts = str.split("\\.");
        return Long.parseLong(parts[0]);
    }

    public String getDecryptedString(String text){
        return aes256.decryptAES256(text);
    }

    public String encryptAES256String(String text) {
        return aes256.encryptAES256(text);
    }

    public LocalDate convertToLocalDate (String dateStr){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return LocalDate.parse(dateStr, formatter);
    }

}
