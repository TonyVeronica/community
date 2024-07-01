package com.service.tonyveronica.controller;

import com.service.tonyveronica.domain.Member;
import com.service.tonyveronica.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.HashMap;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8080"}, allowCredentials = "true")
@RestController
public class UserController {

    @Autowired
    private MemberService memberService;


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
                member = new Member(email, password, nickname, false);

                System.out.println(member);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        System.out.println("성공");
        return new ResponseEntity(member, HttpStatus.OK);
    }
}
