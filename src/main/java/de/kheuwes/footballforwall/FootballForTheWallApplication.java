package de.kheuwes.footballforwall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class FootballForTheWallApplication {

	public static void main(String[] args) {
		SpringApplication.run(FootballForTheWallApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@SuppressWarnings("null")
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				System.out.println("addCorsMappings ...");
				registry.addMapping("/**") // Apply CORS to all paths
						.allowedOrigins("*") // Allow all origins
						.allowedMethods("*") // Allow all HTTP methods
						.allowedHeaders("*"); // Allow all headers
						//.allowCredentials(true); // Allow credentials (cookies, etc.)
				System.out.println("Bean WebMvcConfigurer.corsConfigurer.addCorsMappings");
			}
		};
	}

}
