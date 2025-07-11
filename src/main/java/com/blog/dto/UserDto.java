package com.blog.dto;


import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// use this class for data transfer

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	// @NotEmpty : @NotNull + @NotBlank
	@NotEmpty 
	@Size(min = 3, max = 20, message ="min 3 and max 20 characters are allowed in username !!")
	private String name;
	
	// for validating email with '@.com' use both validation i.e email + pattern
	
	@Email(message ="Invalid Emaill !!")
	@Pattern(regexp=".+@.+\\..+", message ="Invalid Emaill !!")
	private String email;
	
	@NotEmpty
	@Size(min = 3, message ="password must be of minimum 3 characters !!")
	private String password;
	
	@NotEmpty
	private String about;

	
	
}
