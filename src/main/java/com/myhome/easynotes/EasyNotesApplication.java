package com.myhome.easynotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/*
 * This annotation is combination of : 
 * @Configuration 
 * @EnalbeAutoConfiguration
 * @ComponentScan
 */
public class EasyNotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyNotesApplication.class, args);
	}
}
