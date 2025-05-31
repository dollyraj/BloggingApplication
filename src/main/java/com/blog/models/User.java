package com.blog.models;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="users")
@NoArgsConstructor
@Getter
@Setter
public class User{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name ="user_name", nullable = false , length = 30)
	private String name;
	private String email;
	private String password;
	private String about;

	// one user can have do many posts
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch= FetchType.LAZY)
	private List<Post> posts = new ArrayList<>();
	
	// many to many ke case me ek or table extra banega relation handle karne ke liye
	// uska naam apne anusar rakhne ke liye '@JoinTable' use karenge.
	// table ka kon sa field foreign key jaisa kam karega usko btane ke liye
	// '@joinColumns' if writing in same class and 'inverseJoinColumns' if writing in different class.
	
	
	// e.g: User class ka jo table bnega 'user' naam se bnega and iska 'id' field foreign key jaisa kam karega
	// similarly for 'Role' table


}
