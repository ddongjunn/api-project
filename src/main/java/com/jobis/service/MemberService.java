package com.jobis.service;

import com.jobis.common.utils.AES256;
import com.jobis.common.utils.TokenProvider;
import com.jobis.config.security.CustomUserDetailsService;
import com.jobis.domain.dto.*;
import com.jobis.domain.entity.Member;
import com.jobis.domain.entity.Whitelist;
import com.jobis.config.exception.RegistrationNotAllowedException;
import com.jobis.config.exception.UserAlreadyExistsException;
import com.jobis.repository.MemberRepository;
import com.jobis.repository.WhitelistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    @Value("${jwt.secret}")
    private String secret;

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final WhitelistRepository whitelistRepository;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final TokenProvider tokenProvider;


    /**
     * 회원가입
     */
    public SignUpResponseDto join(SignUpRequestDto memberRegistrationDTO){
        checkDuplicateUserId(memberRegistrationDTO.getUserId());
        isAvailableUser(memberRegistrationDTO);

        Member member = new Member(
                memberRegistrationDTO.getUserId(),
                passwordEncoder.encode(memberRegistrationDTO.getPassword()),
                memberRegistrationDTO.getName(),
                encryptAES256String(memberRegistrationDTO.getRegNo())
        );
        memberRepository.save(member);
        return new SignUpResponseDto(member.getUserId(), LocalDateTime.now());
    }

    /**
     * 로그인
     */
    public LoginResponseDto login(LoginRequestDto memberLoginDto) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(memberLoginDto.getUserId(), memberLoginDto.getPassword()));
        }catch (AuthenticationException e){
            throw new BadCredentialsException("아이디와 비밀번호를 확인해주세요.");
        }
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(memberLoginDto.getUserId());

        return new LoginResponseDto(tokenProvider.generateToken(userDetails));
    }

    /**
     * 회원정보 조회
     */
    public MemberInfoResponseDto getMemberInfo()  {
        User user = getCurrentUser();
        String birthdate = getDecryptedRegNoPrefix(user);
        Optional<Member> member = memberRepository.findByUserId(user.getUsername());
        return new MemberInfoResponseDto(member.get().getUserId(), member.get().getName(), birthdate);
    }


    /**
     * 아이디 중복 검사
     */
    private void checkDuplicateUserId(String userId) {
        boolean isDuplicate = memberRepository.findByUserId(userId).isPresent();
        if (isDuplicate) {
            throw new UserAlreadyExistsException("이미 존재하는 아이디입니다.");
        }
    }

    /**
     * 회원 가입 가능 유저 확인
     */
    private void isAvailableUser(SignUpRequestDto memberRegistrationDTO) {
        Optional<Whitelist> joinMember = whitelistRepository.findByUserIdAndRegNo(memberRegistrationDTO.getName(), memberRegistrationDTO.getRegNo());
        joinMember.orElseThrow(() -> new RegistrationNotAllowedException("등록된 사용자가 아닙니다."));
    }

    User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (User) principal;
    }

    private String getDecryptedRegNoPrefix(User user) {
        AES256 aes256 = new AES256();
        Optional<Member> member = memberRepository.findByUserId(user.getUsername());
        try {
            return aes256.decryptAES256RegNo(member.get().getRegNo());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String encryptAES256String(String text) {
        AES256 aes256 = new AES256();
        try {
            return aes256.encryptAES256(text);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

