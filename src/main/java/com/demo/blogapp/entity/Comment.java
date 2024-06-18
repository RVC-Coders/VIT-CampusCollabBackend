package com.demo.blogapp.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="comments")
@NoArgsConstructor
@Data
public class Comment {
  
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer commentId;
	
	@Column(name = "comment", nullable = false)
	@NotEmpty(message = "Comment should not be empty")
	private String content;
	
	
	@ManyToOne
	@JoinColumn(name="post")
	private Post post;
}


