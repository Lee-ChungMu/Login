package com.kosa.login.controller;

import com.kosa.login.dto.MemberRequestDTO;
import com.kosa.login.entity.Member;
import com.kosa.login.security.service.MemberUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.kosa.login.entity.MemberRole.ADMIN;
import static com.kosa.login.entity.MemberRole.USER;
//@Controller
@RestController
@Log4j2
@RequiredArgsConstructor
public class SignController {
    private final MemberUserDetailsService memberUserDetailService;

    @GetMapping("/signUp")
    public void signUpForm(){
        log.info("회원가입--------------------------");
    }

    @PostMapping("/signUp")
    public String signup(Member member, Long isAdmin){

        if(isAdmin == 1){
            member.addMemberRole(ADMIN);
        }
        member.addMemberRole(USER);
        memberUserDetailService.joinMember(member);
        return "redirect:/login";
    }
//    @PostMapping("/signUp/{}")
//    public String signup(@RequestBody MemberRequestDTO request, @RequestParam Long isAdmin){
//
//        //dto에서 entity로 변환
//        if(isAdmin == 1){
//            member.addMemberRole(ADMIN);
//        }
//        member.addMemberRole(USER);
//
//        return memberUserDetailService.joinMember(member);
//    }








































































































































































































































































}
