package com.service.tonyveronica.controller;

import com.fasterxml.jackson.core.JsonToken;
import com.service.tonyveronica.domain.Member;
import com.service.tonyveronica.domain.Post;
import com.service.tonyveronica.dto.CustomMemberDetails;
import com.service.tonyveronica.dto.JoinDTO;
import com.service.tonyveronica.dto.PostCreateDTO;
import com.service.tonyveronica.service.MemberService;
import com.service.tonyveronica.service.PostService;
import lombok.RequiredArgsConstructor;
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
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@RestController
public class PostController {
    private Long imageNumber = 1L;
    private final PostService postService;
    private final MemberService memberService;


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

    @GetMapping("/posts") //전체 게시물 조회 (잠시 보류)
    public ResponseEntity viewAllPosts(){
        System.out.println("전체 게시물 조회!!!!!!!");
        List<Post> list = postService.getAllPosts();
        HttpHeaders headers = new HttpHeaders();

        Map<String, Object>[] returnJsonMap = new HashMap[list.size()];
        for(int i=0;i<list.size();i++){
            returnJsonMap[i] = new HashMap<>();
            Long postId = list.get(i).getPostId();
            String title = list.get(i).getTitle();
            LocalDateTime createdAt = list.get(i).getCreatedAt();
            LocalDateTime updatedAt = list.get(i).getUpdatedAt();
            Long views = list.get(i).getViews();
            Long likes = list.get(i).getLikes();
            returnJsonMap[i].put("postId", postId);
            returnJsonMap[i].put("title", title);
            returnJsonMap[i].put("createdAt", createdAt);
            returnJsonMap[i].put("updatedAt", updatedAt);
            returnJsonMap[i].put("views", views);
            returnJsonMap[i].put("likes", likes);

            Long comments = postService.countComments(postId);
            System.out.println("댓글 수 = " + comments);
            returnJsonMap[i].put("comments", comments);

            String email = list.get(i).getMemberEmail();
            Member m = memberService.isDuplicateEmail(email);

            File file = new File(m.getImagePath());
            if(file.exists()){
                try {
                    Path path = Paths.get(file.getAbsolutePath());
                    byte[] imageBytes = Files.readAllBytes(path);

                    returnJsonMap[i].put("fileName", file.getName());
                    returnJsonMap[i].put("contentType", "image/png");
                    returnJsonMap[i].put("userImage", Base64.getEncoder().encodeToString(imageBytes));

                    headers.setContentType(MediaType.APPLICATION_JSON);

                } catch (IOException e) {
                    e.printStackTrace();
                    returnJsonMap[i].put("message", "Error reading file");
                }
            }
            returnJsonMap[i].put("nickname", m.getNickName());

        }

        return new ResponseEntity<>(returnJsonMap,headers,  HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity viewOnePost(@PathVariable String postId) throws IOException {
        System.out.println("상세 페이지 조회!!!!");
        Long id = Long.parseLong(postId);
        Map<String, Object>responseMap = new HashMap<>();
        Post post = postService.getOnePost(id);
        Member member = memberService.isDuplicateEmail(post.getMemberEmail());

        String title = post.getTitle();
        responseMap.put("title", title);

        String nickName = member.getNickName();
        responseMap.put("nickname", nickName);

        LocalDateTime createdAt = post.getCreatedAt();
        responseMap.put("createdAt", createdAt);

        String content = post.getContent();
        responseMap.put("content", content);

        Long view = post.getViews();
        responseMap.put("view", view);

        Long like = post.getLikes();
        responseMap.put("likes", like);

        Long comments = postService.countComments(id);
        responseMap.put("comment_count", comments);

        File profile = new File(member.getImagePath());
        Path path = Paths.get(profile.getAbsolutePath());
        byte[] imageBytes = Files.readAllBytes(path);
        responseMap.put("userImage", Base64.getEncoder().encodeToString(imageBytes));

        if(post.getPostImagePath() != null){
            File postImage = new File(post.getPostImagePath());
            path = Paths.get(profile.getAbsolutePath());
            imageBytes = Files.readAllBytes(path);
            responseMap.put("postImage",Base64.getEncoder().encodeToString(imageBytes));

        }

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }
}
