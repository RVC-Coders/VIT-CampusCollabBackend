package com.demo.blogapp.payload;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {
	private List<PostDto> content;
	private int pageNumber;
	private long totalElements;
	private int totalPages;
	private int pageSize;
	private boolean lastPage;	
}
