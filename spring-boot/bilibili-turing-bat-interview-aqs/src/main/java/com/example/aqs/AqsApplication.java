package com.example.aqs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

//@EnableOpenApi //Enable open api 3.0.3 spec
@SpringBootApplication
public class AqsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AqsApplication.class, args);
	}

}
