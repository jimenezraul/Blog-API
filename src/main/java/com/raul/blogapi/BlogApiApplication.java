package com.raul.blogapi;
import com.raul.blogapi.model.Comment;
import com.raul.blogapi.model.Post;
import com.raul.blogapi.model.Role;
import com.raul.blogapi.model.User;
import com.raul.blogapi.repository.CommentRepository;
import com.raul.blogapi.repository.PostRepository;
import com.raul.blogapi.repository.RoleRepository;
import com.raul.blogapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class BlogApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(BlogApiApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public CommandLineRunner initData(UserRepository userRepository, RoleRepository roleRepository, PostRepository postRepository, CommentRepository commentRepository) {
		return (args) -> {

			// Create roles
			Role adminRole = new Role("ROLE_ADMIN");
			Role userRole = new Role("ROLE_USER");
			roleRepository.save(adminRole);
			roleRepository.save(userRole);


			// Create users
			User adminUser = new User("Raul","jimenezraul","jimenezraul1981@gmail.com", passwordEncoder().encode("password"), LocalDate.of(1990, 1, 1));

			adminUser.setIsEmailVerified(true);
			User user1 = userRepository.save(adminUser);

			user1.getRoles().add(new Role(adminRole));
			user1.getRoles().add(new Role(userRole));
			userRepository.save(user1);

			User regularUser = new User("Jose","jose","jose@example.com", passwordEncoder().encode("password"), LocalDate.of(1990, 1, 1));
			regularUser.setIsEmailVerified(true);
			User user2 = userRepository.save(regularUser);

			user2.getRoles().add(new Role(userRole));
			userRepository.save(user2);

			// Create posts
			Post post1 = new Post("My New Post Title", "This is the new content of the post with more than 20 characters", user1);
			post1.setCreatedAt();
			post1.setUpdatedAt();
			postRepository.save(post1);

			Post post2 = new Post("My New Post Title 2", "This is the new content of the post with more than 20 characters 2", user2);
			post2.setCreatedAt();
			post2.setUpdatedAt();
			postRepository.save(post2);

			// Create comments
			Comment comment1 = new Comment("Comment 1", post1,user2);
			comment1.setCreatedAt();
			comment1.setUpdatedAt();
			commentRepository.save(comment1);

			Comment comment2 = new Comment("Comment 2", post1, user2);
			comment2.setCreatedAt();
			comment2.setUpdatedAt();
			commentRepository.save(comment2);

			Comment comment3 = new Comment("Comment 3", post2, user1);
			comment3.setCreatedAt();
			comment3.setUpdatedAt();
			commentRepository.save(comment3);

		};
	}



}
