package com.demo.blogapp.payload;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
	
	private Integer commentId;
	
	@NotEmpty(message = "Comment should not be empty")
	private String content;
}
