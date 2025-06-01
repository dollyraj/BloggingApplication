package com.blog.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {


	private String title;

	private String content;

	private String imageName;

	private Date addedDate;

}
