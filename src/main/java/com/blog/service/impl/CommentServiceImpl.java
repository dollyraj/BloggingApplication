package com.blog.service.impl;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.models.Comment;
import com.blog.models.Post;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.dto.CommentDto;
import com.blog.repositories.CommentRepo;
import com.blog.repositories.PostRepo;
import com.blog.service.CommentService;

import java.util.HashSet;
import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	// kya comment karna , kis post pe comment karna h.
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		// 1st find the post 
				Post post = this.postRepo.findById(postId)
							.orElseThrow(()-> new ResourceNotFoundException("Post", "postId",postId));
		
		// is post pe hmko given 'comment' karna h. or comment ke under post ko add karna h.
		// pahle dto se comment me convert karna hoga.
		Comment comment = dtoToComment(commentDto);

		comment.setPost(post);
		Comment savedComment = this.commentRepo.save(comment);
		return commentToDto(savedComment);
	}

	@Override
	public void deleteComment(Integer commentId) {
		// 1st get the comment
		Comment com = commentRepo.findById(commentId)
				.orElseThrow(()-> new ResourceNotFoundException("Comment", "comment id",commentId));
		this.commentRepo.delete(com);
	}

	@Override
	public Set<CommentDto> getCommentsOfPost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "postId",postId));

		Set<Comment> comments=post.getComments();
		Set<CommentDto> commentDtos=new HashSet<>();
		for(Comment c:comments){
			commentDtos.add(commentToDto(c));
		}

		return commentDtos;
	}


	private Comment dtoToComment(CommentDto commentDto){
		return this.modelMapper.map(commentDto,Comment.class);
	}

	private CommentDto commentToDto(Comment comment){
		return this.modelMapper.map(comment,CommentDto.class);
	}

}
