package com.service.tonyveronica.controller;

import com.fasterxml.jackson.core.JsonToken;
import com.service.tonyveronica.domain.Member;
import com.service.tonyveronica.domain.Post;
import com.service.tonyveronica.dto.CustomMemberDetails;
import com.service.tonyveronica.dto.JoinDTO;
import com.service.tonyveronica.dto.PostCreateDTO;
import com.service.tonyveronica.dto.responseDTO;
import com.service.tonyveronica.service.MemberService;
import com.service.tonyveronica.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.Base64Utils;
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

    @GetMapping("/posts/{currentPage}")
    public ResponseEntity<Page<responseDTO>> viewAllPosts(@PathVariable int currentPage) {
        Pageable pageable = PageRequest.of(currentPage, 5);
        Page<Post> postPage = postService.getAllPosts(pageable);
        List<responseDTO> responseDTOList = new ArrayList<>();

        for (Post post : postPage) {
            Long postId = post.getPostId();
            String title = post.getTitle();
            LocalDateTime createdAt = post.getCreatedAt();
            LocalDateTime updatedAt = post.getUpdatedAt();
            Long views = post.getViews();
            Long likes = post.getLikes();
            Long comments = postService.countComments(postId);
            String email = post.getMemberEmail();
            Member member = memberService.isDuplicateEmail(email);

            String userImage = null;
            if (member != null) {
                File file = new File(member.getImagePath());
                if (file.exists()) {
                    try {
                        Path path = Paths.get(file.getAbsolutePath());
                        byte[] imageBytes = Files.readAllBytes(path);
                        userImage = Base64.getEncoder().encodeToString(imageBytes);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            responseDTO responseDTO = new responseDTO(postId, title, createdAt, updatedAt, views, likes, comments, email, userImage, member != null ? member.getNickName() : null);
            responseDTOList.add(responseDTO);
        }

        Page<responseDTO> responseDTOPage = new PageImpl<>(responseDTOList, pageable, postPage.getTotalElements());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(responseDTOPage, headers, HttpStatus.OK);
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
        responseMap.put("views", view);

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
            path = Paths.get(postImage.getAbsolutePath());
            imageBytes = Files.readAllBytes(path);
            responseMap.put("postImage",Base64.getEncoder().encodeToString(imageBytes));
        }

        responseMap.put("postId", id);

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }
}
