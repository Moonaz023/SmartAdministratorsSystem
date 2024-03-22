package com.sas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.sas.*"})
public class SmartAdministratorsSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartAdministratorsSystemApplication.class, args);
	}

}
