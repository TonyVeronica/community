package com.service.tonyveronica.repository;

import com.service.tonyveronica.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
