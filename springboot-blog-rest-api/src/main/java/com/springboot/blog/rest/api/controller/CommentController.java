package com.springboot.blog.rest.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.rest.api.payload.CommentDto;
import com.springboot.blog.rest.api.service.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	private CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postId") Long postId,
			@Valid @RequestBody CommentDto commentDto) {
		System.out.println(commentDto);
		return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
	}

	@GetMapping("/posts/{postId}/comments")
	public List<CommentDto> getCommentByPostId(@PathVariable(name = "postId") Long postId) {
		return commentService.getCommentByPostId(postId);
	}

	@GetMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") Long postId,
			@PathVariable("commentId") Long commentId) {
		CommentDto commentDto = commentService.getCommentById(postId, commentId);
		return new ResponseEntity<>(commentDto, HttpStatus.OK);

	}

	// update comment
	@PutMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable("postId") Long postId, @PathVariable("id") Long id,
			@Valid @RequestBody CommentDto commentDto) {
		CommentDto updatedComment = commentService.updateComment(postId, id, commentDto);
		return new ResponseEntity<>(updatedComment, HttpStatus.OK);
	}

	// delete comment
	@DeleteMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable("id") Long commentId) {
		commentService.deleteComment(postId, commentId);
		return new ResponseEntity<>("comment deleted successfull!!", HttpStatus.OK);
	}

}
