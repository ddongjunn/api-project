package com.jobis.service;

<<<<<<< HEAD
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobis.common.utils.AES256;
import com.jobis.domain.dto.ScrapRequestDto;
import com.jobis.domain.entity.Member;
import com.jobis.domain.entity.Salary;
=======
import com.jobis.common.utils.AES256;
import com.jobis.common.utils.TokenProvider;
import com.jobis.config.exception.UserNotFoundException;
import com.jobis.domain.dto.*;
import com.jobis.domain.entity.IncomeDeduction;
import com.jobis.domain.entity.Member;
import com.jobis.domain.entity.Salary;
import com.jobis.repository.IncomeDeductionRepository;
>>>>>>> 3f7faa693bab81d752a5d73620b743a73b93943f
import com.jobis.repository.MemberRepository;
import com.jobis.repository.SalaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
<<<<<<< HEAD
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;
import javax.swing.RepaintManager;

import java.io.DataInput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
=======
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
>>>>>>> 3f7faa693bab81d752a5d73620b743a73b93943f
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScrapService {

<<<<<<< HEAD
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final SalaryRepository salaryRepository;

    public String scrap() throws Exception {
        String responseData = callScrapApi();
        System.out.println(responseData);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(responseData);
        JSONObject data = (JSONObject) jsonObject.get("data");
        JSONObject jsonList = (JSONObject) data.get("jsonList");
        JSONArray jsonArrSalary = (JSONArray) jsonList.get("급여");

        log.info("jsonArrSalary size={} get={}", jsonArrSalary.size(), jsonArrSalary.get(0));

        for (int i = 0; i < jsonArrSalary.size(); i++) {
            JSONObject tmp = (JSONObject) jsonArrSalary.get(i);

            String str1 = (String) tmp.get("소득내역");
        }

        // JSONObject salaryObj = (JSONObject) jsonSalary.get(0);

        // User user = memberService.getCurrentUser();
        // Optional<Member> member = memberRepository.findByUserId(user.getUsername());
        // Salary salary = Salary.builder()
        // .member(member.get())
        // .incomeDetail((String) salaryObj.get("총지급액"))
        // .workStartDate(convertToLocalDateTime((String) salaryObj.get("업무시작일")))
        // .companyName((String) salaryObj.get("기업명"))
        // .build();

        // salaryRepository.save(salary);

        // System.out.println("size = " + jsonSalary.size());
        // for(int i=0; i<jsonSalary.size(); i++){
        // System.out.println(jsonSalary.get(i));
        // }

        String jsonCalculationAmount = (String) jsonList.get("산출세액");
        /*
         * String responseData = callScrapApi();
         * JSONParser jsonParser = new JSONParser();
         * JSONObject jsonObject = (JSONObject) jsonParser.parse(responseData);
         * JSONObject data = (JSONObject) jsonObject.get("data");
         * JSONObject jsonList = (JSONObject) data.get("jsonList");
         * JSONArray jsonSalary = (JSONArray) jsonList.get("급여");
         * String jsonCalculationAmount = (String) jsonList.get("산출세액");
         * JSONArray jsonIncomeDeduction = (JSONArray) jsonList.get("소득공제");
         * 
         * ObjectMapper objectMapper = new ObjectMapper();
         * jsonSalary.
         * 
         * log.info("salary = {}",salary.toString());
         */
        return "ggg";
    }

    public String callScrapApi() throws Exception {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://codetest.3o3.co.kr/v2/scrap")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer JwtToken")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        User user = memberService.getCurrentUser();
        Optional<Member> member = memberRepository.findByUserId(user.getUsername());
        ScrapRequestDto scrapRequestDto = new ScrapRequestDto(member.get().getName(),
                getDecryptedString(member.get().getRegNo()));

        return webClient
                .post()
                .bodyValue(scrapRequestDto)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> log.error("call scrap api error = {}", e))
                .block();
    }

    private String getDecryptedString(String text) throws Exception {
        AES256 aes256 = new AES256();
        return aes256.decryptAES256(text);
    }

    private String encryptAES256String(String text) throws Exception {
        AES256 aes256 = new AES256();
        return aes256.encryptAES256(text);
    }

    private LocalDateTime convertToLocalDateTime(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return LocalDateTime.parse(dateStr, formatter);
    }
=======
    private final MemberRepository memberRepository;
    private final SalaryRepository salaryRepository;
    private final IncomeDeductionRepository incomeDeductionRepository;
    private final TokenProvider tokenProvider;

    public ScrapResponse scrap(HttpServletRequest request)  {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
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

    public String getDecryptedString(String text)  {
        AES256 aes256 = new AES256();
        try {
            return aes256.decryptAES256(text);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String encryptAES256String(String text) {
        AES256 aes256 = new AES256();
        try {
            return aes256.encryptAES256(text);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public LocalDate convertToLocalDate (String dateStr){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return LocalDate.parse(dateStr, formatter);
    }

>>>>>>> 3f7faa693bab81d752a5d73620b743a73b93943f
}
