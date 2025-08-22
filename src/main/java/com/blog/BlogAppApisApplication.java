package com.blog;


import com.blog.config.AppConstants;
import com.blog.models.Role;
import com.blog.models.User;
import com.blog.repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.List;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {
	// all this inside this will run just after start of application.
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

    @Override
	public void run(String... args) throws Exception {

		try {
			Role role = new Role();
			role.setId(AppConstants.ADMIN);
			role.setName("ADMIN");

			Role role1 = new Role();
			role1.setId(AppConstants.USER);
			role1.setName("NORMAL");

			List<Role> roles = List.of(role, role1);
			List<Role> result = this.roleRepo.saveAll(roles);
			result.forEach(r -> {
				System.out.println(r.getName());
			});
		}catch(Exception e)
		{
			e.printStackTrace();
		}

	}



}




