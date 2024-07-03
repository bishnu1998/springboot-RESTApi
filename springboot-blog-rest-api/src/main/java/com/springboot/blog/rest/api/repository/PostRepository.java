package com.springboot.blog.rest.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.blog.rest.api.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
