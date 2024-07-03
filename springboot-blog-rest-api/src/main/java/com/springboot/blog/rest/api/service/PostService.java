package com.springboot.blog.rest.api.service;



import com.springboot.blog.rest.api.payload.PostDto;
import com.springboot.blog.rest.api.payload.PostResponse;

public interface PostService {
	PostDto createPost(PostDto postDto);

	PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir);

	PostDto getPostById(Long id);

	PostDto updatePost(PostDto postDto, Long id);

	void deleteById(Long id);

}
