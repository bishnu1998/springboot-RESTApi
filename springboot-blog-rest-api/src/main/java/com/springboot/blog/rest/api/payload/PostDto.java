package com.springboot.blog.rest.api.payload;

import java.util.Set;

import javax.validation.constraints.*;



public class PostDto {
	private Long id;
	// title should not be null or empty
	// title should be at least 2 character
	@NotEmpty
	@Size(min = 2, message = "title should be at least 2 character")
	private String title;

	@NotEmpty
	@Size(min = 10, message = "post description mush be at least10 character")
	private String description;
	@NotEmpty
	private String content;
	private Set<CommentDto> comments;

	public Set<CommentDto> getComments() {
		return comments;
	}

	public void setComments(Set<CommentDto> comments) {
		this.comments = comments;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getContent() {
		return content;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
