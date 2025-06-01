package com.blog.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer postId;
	
	@Column(name= "post_title", length = 100, nullable = false)
	private String title;   
	
	@Column(length = 10000)
	private String content;
	
	private String imageName;
	
	private Date addedDate;
	
	// many post can map to single category
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;  // post kis category ka h
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;         // post kis user ka h
	
	
	// why do we use mappedBy
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Set<Comment> comments = new HashSet<>();
	
}
