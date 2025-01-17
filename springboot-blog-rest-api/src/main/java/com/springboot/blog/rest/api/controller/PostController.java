package com.springboot.blog.rest.api.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.rest.api.payload.PostDto;
import com.springboot.blog.rest.api.payload.PostResponse;
import com.springboot.blog.rest.api.service.PostService;


@RestController
@RequestMapping("/api/posts")
public class PostController {

	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	// create the new post
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
		return new ResponseEntity<PostDto>(postService.createPost(postDto), HttpStatus.CREATED);
	}

	// get all the posts
	@GetMapping
	public PostResponse getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = "AppConstants.DEFAULT_PAGE_NUMBER", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "AppConstants.DEFAULT_PAGE_SIZE", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "AppConstants.DEFAULT_SORT_BY", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "AppConstants.DEFAULT_SORT_DIRECTION", required = false) String sortDir) {
		return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
	}

	// get posts by id
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(postService.getPostById(id));
	}

	// update post by id
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") Long id) {
		PostDto postResponse = postService.updatePost(postDto, id);
		return ResponseEntity.ok(postResponse);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id) {
		postService.deleteById(id);
		return new ResponseEntity<String>("post entity deleted successfully!", HttpStatus.OK);
	}
}
