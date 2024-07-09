package com.service.tonyveronica.controller;

import com.service.tonyveronica.domain.Member;
import com.service.tonyveronica.dto.CustomMemberDetails;
import com.service.tonyveronica.dto.JoinDTO;
import com.service.tonyveronica.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8080"}, allowCredentials = "true")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final MemberService memberService;


    @PostMapping("/users")
    public ResponseEntity registUser(@RequestBody HashMap<String, Object>requestJsonHashMap){ //회원가입
        JoinDTO joinDTO = null;
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
                joinDTO = new JoinDTO(email, password, nickname, file.getAbsolutePath());
                System.out.println(joinDTO);

                Long memberId = memberService.join(joinDTO);
                System.out.println("memberId = " + memberId);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        System.out.println("성공");
        return new ResponseEntity(joinDTO, HttpStatus.OK);
    }

    @PostMapping("users/email") //이메일 중복 확인
    public ResponseEntity isDuplicateEmail(@RequestBody HashMap<String, Object>requestJsonHashMap){
        System.out.println(requestJsonHashMap);
        String email = (String)requestJsonHashMap.get("email");
        System.out.println("email = " + email);

        Member member = memberService.isDuplicateEmail(email);
        System.out.println(member);

        if(member != null){ //중복
            return new ResponseEntity(member, HttpStatus.CONFLICT);
        }
        else return new ResponseEntity(member, HttpStatus.OK);
    }

    @PostMapping("users/nickname") //닉네임 중복 확인
    public ResponseEntity isDuplicateNickname(@RequestBody HashMap<String, Object>requestJsonHashMap) {
        System.out.println(requestJsonHashMap);
        String nickname = (String) requestJsonHashMap.get("nickname");
        System.out.println("nickname = " + nickname);

        Member member = memberService.isDuplicateNickname(nickname);
        System.out.println(member);

        if (member != null) { //중복
            return new ResponseEntity(member, HttpStatus.CONFLICT);
        } else return new ResponseEntity(member, HttpStatus.OK);
    }

    @GetMapping("/users/user")
    public ResponseEntity viewMemberInfo(Authentication authentication){
        // 인증된 사용자의 정보를 가져옵니다.
        CustomMemberDetails customMemberDetails = (CustomMemberDetails) authentication.getPrincipal();
        String username = customMemberDetails.getUsername();
        System.out.println("username = " + username);

        Member member = memberService.isDuplicateEmail(username);


        Map<String, Object> response = new HashMap<>();
        response.put("email", member.getEmail());
        response.put("nickname", member.getNickName());
        response.put("imagePath", member.getImagePath());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/users/user/image") //이미지 조회
    public ResponseEntity<Resource> getUserImage(@RequestParam String imagePath) {
        System.out.println("이미지 경로 = " + imagePath);
        File file = new File(imagePath);
        if (file.exists()) {
            Resource resource = new FileSystemResource(file);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .contentType(MediaType.parseMediaType("image/png"))
                    .body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PatchMapping("/users/user/profile")
    public ResponseEntity updateUserInfo(@RequestBody HashMap<String, Object>requestJsonHashMap, Authentication authentication){
        System.out.println("회원 정보 업데이트 시작");

        CustomMemberDetails customMemberDetails = (CustomMemberDetails) authentication.getPrincipal();
        String email = customMemberDetails.getUsername();

        String profileImageBase64 = (String) requestJsonHashMap.get("profileImage");
        System.out.println(profileImageBase64);
        //이미지가 null일 경우 다시 처리해야 함

        if ((profileImageBase64 != null && !profileImageBase64.isEmpty()) && profileImageBase64.charAt(0) == 'd') {
            // Base64 헤더 제거
            String base64Image = profileImageBase64.split(",")[1];
            try {
                // Base64 디코딩
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                System.out.println(imageBytes);

                // 파일 저장
                File file = new File("image/"+email+".png");
                try (OutputStream os = new FileOutputStream(file)) {
                    os.write(imageBytes);
                }

                System.out.println("이미지 저장 성공: " + file.getAbsolutePath());

            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        System.out.println("email = " + email);

        String nickname = (String)requestJsonHashMap.get("nickname");
        System.out.println("nickname = " + nickname);

        int isSuccess = memberService.updateNickname(nickname, email);
        System.out.println(isSuccess);

        System.out.println("성공");
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("users/user/password")
    public ResponseEntity updateUserPassword(@RequestBody HashMap<String, Object>requestJsonHashMap, Authentication authentication){
        System.out.println("비밀 번호 수정!!!");

        CustomMemberDetails customMemberDetails = (CustomMemberDetails) authentication.getPrincipal();
        String email = customMemberDetails.getUsername();
        String password = (String)requestJsonHashMap.get("password");
        System.out.println("새로운 비밀 번호 = " + password);

        int isSuccess = memberService.updatePassword(password, email);
        System.out.println("isSuccess = " + isSuccess);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("users/user/delete")
    public ResponseEntity withdrawMember(Authentication authentication){
        System.out.println("회원 정보 탈퇴");

        CustomMemberDetails customMemberDetails = (CustomMemberDetails) authentication.getPrincipal();
        String email = customMemberDetails.getUsername();

        int isSuccess = memberService.deleteMember(email);
        System.out.println("isSuccess = " + isSuccess);

        //회원 탈퇴하고 access token 삭제 해야함

        return new ResponseEntity(HttpStatus.OK);
    }
}
