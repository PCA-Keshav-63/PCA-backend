package com.businesslisting.pca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PcaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PcaApplication.class, args);
	}

}
  