 package com.blog.service;

import com.blog.dto.CommentDto;

import java.util.List;
import java.util.Set;

 public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto , Integer postId);
	
	void deleteComment(Integer commentId);

	 //get all comments of post
	 Set<CommentDto> getCommentsOfPost(Integer postId);

}
