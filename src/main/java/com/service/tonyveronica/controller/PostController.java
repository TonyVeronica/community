package com.service.tonyveronica.controller;

import com.fasterxml.jackson.core.JsonToken;
import com.service.tonyveronica.domain.Post;
import com.service.tonyveronica.dto.CustomMemberDetails;
import com.service.tonyveronica.dto.JoinDTO;
import com.service.tonyveronica.dto.PostCreateDTO;
import com.service.tonyveronica.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {
    private Long imageNumber = 1L;
    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity resistPost(@RequestBody HashMap<String, Object> requestJsonMap, Authentication authentication){
        System.out.println("포스트 요청 들어옴");
        PostCreateDTO postCreateDTO = null;
        System.out.println(requestJsonMap);
        String title = (String)requestJsonMap.get("title");
        String content = (String)requestJsonMap.get("content");
        String image = (String)requestJsonMap.get("image");

        CustomMemberDetails customMemberDetails = (CustomMemberDetails) authentication.getPrincipal();
        String email = customMemberDetails.getUsername();

        if(image != null) { //사진 들어왔을 경우
            // Base64 헤더 제거
            String base64Image = image.split(",")[1];
            try {
                // Base64 디코딩
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                System.out.println(imageBytes);

                // 파일 저장
                File file = new File("image/post_image/" + email + Long.toBinaryString(imageNumber) + ".png");
                try (OutputStream os = new FileOutputStream(file)) {
                    os.write(imageBytes);
                }
                imageNumber++;
                System.out.println("이미지 저장 성공: " + file.getAbsolutePath());


                postCreateDTO = new PostCreateDTO(title, content, file.getAbsolutePath());
                System.out.println(postCreateDTO);

                Long postId = postService.createPost(postCreateDTO, email);
                System.out.println("postId = " + postId);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else{
            postCreateDTO = new PostCreateDTO(title, content, image);
            Long postId = postService.createPost(postCreateDTO, email);
        }

        return new ResponseEntity(postCreateDTO, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity viewAllPosts(){
        System.out.println("전체 게시물 조회!!!!!!!");
        List<Post> list = postService.getAllPosts();
        System.out.println(list);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
