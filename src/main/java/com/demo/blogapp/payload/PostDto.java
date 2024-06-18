package com.demo.blogapp.payload;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
    
	private Integer postId;
    private String postTitle;
    private String content;
    private String imageName; // automatically added
    private Date addedDate;   // automatically added
    private CategoryDto category;
    private UserDto user;
 
}
