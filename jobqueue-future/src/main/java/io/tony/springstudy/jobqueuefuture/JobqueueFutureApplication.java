package io.tony.springstudy.jobqueuefuture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JobqueueFutureApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobqueueFutureApplication.class, args);
	}

}
