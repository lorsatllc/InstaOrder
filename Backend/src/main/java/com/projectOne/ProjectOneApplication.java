package com.projectOne;

import com.projectOne.security.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.projectOne")
@Import(SecurityConfig.class)
public class ProjectOneApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectOneApplication.class, args);
	}

}
