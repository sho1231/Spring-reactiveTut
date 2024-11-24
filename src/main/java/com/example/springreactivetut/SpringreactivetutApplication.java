package com.example.springreactivetut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringreactivetutApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(SpringreactivetutApplication.class, args);
		}
		catch (Exception e){
			System.out.println("Error:"+e.getMessage());
		}
	}

}
