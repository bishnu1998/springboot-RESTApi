package com.springboot.blog.rest.api.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CommentDto {
	private Long id;
	@NotEmpty(message = "name should not be null or empty")
	private String name;
	@NotEmpty(message = "email should not be null or empty")
	@Email
	private String email;
	@NotEmpty
	@Size(min = 10, message = "body con't be less than 10 character")
	private String body;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getBody() {
		return body;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
