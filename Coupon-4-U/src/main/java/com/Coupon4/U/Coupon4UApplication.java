package com.Coupon4.U;

import com.Coupon4.U.services.ClientService;
import com.Coupon4.U.test.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;

@SpringBootApplication
public class Coupon4UApplication implements CommandLineRunner {

	@Autowired
	private ApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(Coupon4UApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Test test = context.getBean(Test.class);
		test.testAll();
	}

	@Bean
	public HashMap<String, ClientService> tokenStore(){
		return new HashMap<>();
	}
}
