package com.springboot.blog.rest.api.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.rest.api.entity.Comment;
import com.springboot.blog.rest.api.entity.Post;
import com.springboot.blog.rest.api.exception.BlogAPIException;
import com.springboot.blog.rest.api.exception.ResourceNotFoundException;
import com.springboot.blog.rest.api.payload.CommentDto;
import com.springboot.blog.rest.api.repository.CommentRepository;
import com.springboot.blog.rest.api.repository.PostRepository;
import com.springboot.blog.rest.api.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private ModelMapper mapper;

	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper = mapper;

	}

	@Override
	public CommentDto createComment(Long postId, CommentDto commentDto) {

		Comment comment = mapToEntity(commentDto);

		// retrieve post entity by id
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// set post to comment entity
		comment.setPost(post);

		// comment entity in to DB
		Comment newComment = commentRepository.save(comment);

		return mapToDTO(newComment);
	}

	private CommentDto mapToDTO(Comment comment) {
		
		CommentDto commentDto = mapper.map(comment, CommentDto.class);
		
		// CommentDto commentDto = new CommentDto();
//		commentDto.setId(comment.getId());
//		commentDto.setName(comment.getName());
//		commentDto.setEmail(comment.getEmail());
//		commentDto.setBody(comment.getBody());

		return commentDto;
	}

	private Comment mapToEntity(CommentDto commentDto) {
		Comment comment = mapper.map(commentDto, Comment.class);
		
//		Comment comment = new Comment();
//		comment.setId(commentDto.getId());
//		comment.setName(commentDto.getName());
//		comment.setEmail(commentDto.getEmail());
//		comment.setBody(commentDto.getBody());

		return comment;
	}

	@Override
	public List<CommentDto> getCommentByPostId(Long postId) {
		// retrieve comments by postId
		List<Comment> comments = commentRepository.findByPostId(postId);
		// convert list of comments entities into list of comments dto's
		return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDto getCommentById(Long postId, Long commentId) {
		// retrieve the post entity by id
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// retrieve the comment entity by id
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "COMMENT DOES NOT BELONGS TO POST");
		}

		return mapToDTO(comment);
	}

	@Override
	public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
		// retrieve the post entity by id
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// retrieve the comment entity by id
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "COMMENT DOES NOT BELONGS TO POST");
		}

		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());

		// update the comment in DB
		Comment updatedComment = commentRepository.save(comment);
		return mapToDTO(updatedComment);
	}

	@Override
	public void deleteComment(Long postId, Long commentId) {
		// retrieve the post entity by id
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// retrieve the comment entity by id
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "COMMENT DOES NOT BELONGS TO POST");
		}
		commentRepository.delete(comment);

	}

}
