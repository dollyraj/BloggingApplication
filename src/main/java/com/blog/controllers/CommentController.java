package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.blog.dto.ApiResponse;
import com.blog.dto.CommentDto;
import com.blog.service.CommentService;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(
			@RequestBody CommentDto commentDto,
			@PathVariable Integer postId
			){
		CommentDto createdComment = this.commentService.createComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(createdComment, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId)
	{
		this.commentService.deleteComment(commentId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully!!", true), HttpStatus.OK);
		
	}

	//getAllCommentsOfPost
	@GetMapping("/posts/{postId}/comments")
	public ResponseEntity<Set<CommentDto>> getCommentsOfPosts(@PathVariable Integer postId ){
		Set<CommentDto> result = this.commentService.getCommentsOfPost(postId);
		return new ResponseEntity<Set<CommentDto>> (result, HttpStatus.OK);
	}

}
