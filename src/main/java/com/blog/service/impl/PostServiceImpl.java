package com.blog.service.impl;

import java.util.*;

import java.util.stream.Collectors;

import com.blog.dto.CommentDto;
import com.blog.models.Comment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.models.Category;
import com.blog.models.Post;
import com.blog.models.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.dto.PostDto;
import com.blog.dto.PostResponse;
import com.blog.repositories.CategoryRepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.UserRepo;
import com.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User", "userId",userId));
		
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId",categoryId));
		
		Post post = dtoToPost(postDto);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post newPost = this.postRepo.save(post);


		return postToDto(newPost);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		// 1st find the post 
		Post post = this.postRepo.findById(postId)
					.orElseThrow(()-> new ResourceNotFoundException("Post", "postId",postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatedPost= this.postRepo.save(post);
		return postToDto(updatedPost);
	}

	@Override
	public void deletePost(Integer postId) {
		// 1st find the post 
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "postId",postId));
		this.postRepo.delete(post);
	}
	
	// will sort asc to the field that we will pass in place of 'sortBy' while calling api.
	// 'sortDir' is for sorting in either ascending or descending order.
	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy,String sortDir) {
		System.out.println("pageNumber= "+pageNumber+",pageSize="+pageSize+",sortBy="+sortBy+",sortDir="+sortDir);
		
//		Sort sort = null;
//		if(sortDir.equalsIgnoreCase("asc"))
//		{
//			sort= Sort.by(sortBy).ascending();
//		}
//		else
//		{
//			sort= Sort.by(sortBy).descending();
//		}
		
		// shortcut using ternary operator
		Sort sort = (sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending());
		
		Pageable p = PageRequest.of(pageNumber, pageSize, sort); // getting the pageable object
		Page<Post> pagePost = this.postRepo.findAll(p); // is object ke help se us page ka details nikal sakte h.
		List<Post> allPosts = pagePost.getContent(); // to get all post on that page

		List<PostDto> postDtos=new ArrayList<>();

		for(Post post:allPosts){
			postDtos.add(postToDto(post));
		}
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "postId",postId));
		return postToDto(post);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		// first get the category from database
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId",categoryId));
		// get the posts of that category
		List<Post> posts = this.postRepo.findByCategory(cat);
		List<PostDto> postDtos=new ArrayList<>();

		for(Post post:posts){
			postDtos.add(postToDto(post));
		}

		return postDtos;
	}

	//getAllPostOfAUser
	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		// first get the category from database
		User user = this.userRepo.findById(userId)
						.orElseThrow(()-> new ResourceNotFoundException("User", "userId",userId));
		// get the posts of that category
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> postDtos=new ArrayList<>();

		for(Post post:posts){
			postDtos.add(postToDto(post));
		}

		return postDtos;
	}
	
	
	@Override
	public List<PostDto> searchPosts(String keyword) {
		// agar 'title' me ye 'keyword' dikha to us post ko show kar dega.
		List<Post>  posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos=new ArrayList<>();

		for(Post post:posts){
			postDtos.add(postToDto(post));
		}

		return postDtos;
	}



	public PostDto postToDto(Post post){
		return this.modelMapper.map(post, PostDto.class);
	}

	public Post dtoToPost(PostDto postDto){
		return this.modelMapper.map(postDto, Post.class);
	}

	//List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());


}
