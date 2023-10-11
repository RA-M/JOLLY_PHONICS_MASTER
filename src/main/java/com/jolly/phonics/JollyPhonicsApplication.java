package com.jolly.phonics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//git@github.com:RA-M/JOLLY_PHONICS_MASTER.git
@SpringBootApplication
public class JollyPhonicsApplication {
   /* @Autowired
    private UserRepository repository;

    @PostConstruct
    public void initUsers() {
        List<User> users = Stream.of(
                new User(101, "Ram", "Ram123", "javatechie@gmail.com"),
                new User(102, "user1", "pwd1", "user1@gmail.com"),
                new User(103, "user2", "pwd2", "user2@gmail.com"),
                new User(104, "user3", "pwd3", "user3@gmail.com")
        ).collect(Collectors.toList());
        repository.saveAll(users);
    }*/

    public static void main(String[] args) {
        SpringApplication.run(JollyPhonicsApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
    	return new WebMvcConfigurer(){
    		@Override
    	    public void addCorsMappings(CorsRegistry registry) {
    	        registry.addMapping("/**")
    	            .allowedOrigins("http://localhost:4200") // Replace with your Angular app's URL
    	            .allowedMethods("GET", "POST", "PUT", "DELETE")
    	            .allowedHeaders("Authorization", "Content-Type")
    	            .allowCredentials(true)
    	            .maxAge(3600); // Define the maximum age of cached pre-flight requests (in seconds)
    	    }
    	};
    }
}
