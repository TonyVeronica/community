package com.service.tonyveronica.controller;

import com.service.tonyveronica.domain.Member;
import com.service.tonyveronica.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.HashMap;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8080"}, allowCredentials = "true")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final MemberService memberService;


    @PostMapping("/users")
    public ResponseEntity registUser(@RequestBody HashMap<String, Object>requestJsonHashMap){ //회원가입
        Member member = null;
        String profileImageBase64 = (String) requestJsonHashMap.get("profileImage");
        if (profileImageBase64 != null && !profileImageBase64.isEmpty()) {
            // Base64 헤더 제거
            String base64Image = profileImageBase64.split(",")[1];
            try {
                // Base64 디코딩
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                System.out.println(imageBytes);

                // 파일 저장
                String userEmail = (String)requestJsonHashMap.get("email");
                File file = new File("image/"+userEmail+".png");
                try (OutputStream os = new FileOutputStream(file)) {
                    os.write(imageBytes);
                }

                System.out.println("이미지 저장 성공: " + file.getAbsolutePath());

                String email = (String)requestJsonHashMap.get("email");
                String password = (String)requestJsonHashMap.get("password");
                String nickname = (String)requestJsonHashMap.get("nickname");
                member = new Member(email, password, nickname, file.getAbsolutePath(), false);
                System.out.println(member);

                Long memberId = memberService.join(member);
                System.out.println("memberId = " + memberId);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        System.out.println("성공");
        return new ResponseEntity(member, HttpStatus.OK);
    }

    @PostMapping("users/email") //이메일 중복 확인
    public ResponseEntity isDuplicateEmail(@RequestBody HashMap<String, Object>requestJsonHashMap){
        System.out.println(requestJsonHashMap);
        String email = (String)requestJsonHashMap.get("email");
        System.out.println("email = " + email);

        Member member = memberService.isDuplicateEmail(email);
        System.out.println(member);

        if(member != null){ //중복
            return new ResponseEntity(member, HttpStatus.UNAUTHORIZED);
        }
        else return new ResponseEntity(member, HttpStatus.OK);
    }

    @PostMapping("users/nickname") //이메일 중복 확인
    public ResponseEntity isDuplicateNickname(@RequestBody HashMap<String, Object>requestJsonHashMap) {
        System.out.println(requestJsonHashMap);
        String nickname = (String) requestJsonHashMap.get("nickname");
        System.out.println("nickname = " + nickname);

        Member member = memberService.isDuplicateNickname(nickname);
        System.out.println(member);

        if (member != null) { //중복
            return new ResponseEntity(member, HttpStatus.UNAUTHORIZED);
        } else return new ResponseEntity(member, HttpStatus.OK);
    }

}
