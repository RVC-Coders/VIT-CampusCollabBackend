package com.demo.blogapp;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.demo.blogapp.entity.Role;
import com.demo.blogapp.repository.RoleRepository;
import com.demo.blogapp.utils.AppConstant;

@SpringBootApplication
public class BlogAppApiApplication implements CommandLineRunner{
	
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;
	
    @Autowired
	public BlogAppApiApplication(PasswordEncoder passwordEncoder,RoleRepository roleRepository) {
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApiApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(passwordEncoder.encode("abhi"));
		
		try {
			Role role = new Role();
			role.setId(AppConstant.ADMIN_USER);
			role.setName("ROLE_ADMIN");
			
			Role role2 = new Role();
			role2.setId(AppConstant.NORMAL_USER);
			role2.setName("ROLE_NORMAL");
			
			List<Role> roles = List.of(role,role2);
			
			List<Role> resultRoles = roleRepository.saveAll(roles);
			
			resultRoles.forEach( r ->{
				System.out.println(r.getName());
			});
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
