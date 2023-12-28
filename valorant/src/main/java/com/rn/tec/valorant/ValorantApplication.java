package com.rn.tec.valorant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class ValorantApplication {

	public static void main(String[] args) {
		SpringApplication.run(ValorantApplication.class, args);
	}

}
