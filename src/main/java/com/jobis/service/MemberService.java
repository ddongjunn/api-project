package com.jobis.service;

import com.jobis.domain.dto.MemberRegistrationDTO;
import com.jobis.domain.entity.Member;
import com.jobis.domain.entity.Whitelist;
import com.jobis.exception.RegistrationNotAllowedException;
import com.jobis.exception.UserAlreadyExistsException;
import com.jobis.repository.MemberRepository;
import com.jobis.repository.WhitelistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    @Value("${jwt.secret}")
    private String secret;

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final WhitelistRepository whitelistRepository;

    /**
     * 회원가입
     */
    public String join(MemberRegistrationDTO memberRegistrationDTO){
        validateDuplicateMember(memberRegistrationDTO);
        if(!isAvailableUser(memberRegistrationDTO)){
            throw new RegistrationNotAllowedException("등록된 사용자가 아닙니다.");
        }
        Member member = new Member(
                memberRegistrationDTO.getUserId(),
                passwordEncoder.encode(memberRegistrationDTO.getPassword()),
                memberRegistrationDTO.getName(),
                passwordEncoder.encode(memberRegistrationDTO.getRegNo())
        );
        memberRepository.save(member);
        return member.getUserId();
    }

    private void validateDuplicateMember(MemberRegistrationDTO memberRegistrationDTO) {
        Optional<Member> userId = memberRepository.findByUserId(memberRegistrationDTO.getUserId());
        if(userId.isPresent()){
            throw new UserAlreadyExistsException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 가입 가능 유저 확인
     */
    private boolean isAvailableUser(MemberRegistrationDTO memberRegistrationDTO){
        System.out.println("username = " + memberRegistrationDTO.getName());
        System.out.println("regNo = " + memberRegistrationDTO.getRegNo());
        Optional<Whitelist> joinMember = whitelistRepository.findByUserIdAndRegNo(memberRegistrationDTO.getName(), memberRegistrationDTO.getRegNo());
        return joinMember.isPresent();
    }
}
