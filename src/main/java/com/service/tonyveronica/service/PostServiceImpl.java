package com.service.tonyveronica.service;

import com.service.tonyveronica.domain.Post;
import com.service.tonyveronica.dto.PostCreateDTO;
import com.service.tonyveronica.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    @Override
    public Long createPost(PostCreateDTO postCreateDTO, String email) {
        String title = postCreateDTO.getTitle();
        String content = postCreateDTO.getContent();
        String postImagePath = postCreateDTO.getPostImagePath();

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setPostImagePath(postImagePath);
        post.setDeleted(false);
        post.setMemberEmail(email);
        post.setViews(0L);

        return postRepository.save(post).getPostId();
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
