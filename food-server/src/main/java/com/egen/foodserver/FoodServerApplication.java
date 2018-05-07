package com.egen.foodserver;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FoodServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodServerApplication.class, args); // $COVERAGE-IGNORE$
	}

	//init data
//	@Bean
//	ApplicationRunner applicationRunner(FoodRepository foodRepository){
//		return args -> {
//			foodRepository.save(new Food(null, "Rice", "White Rice"));
//		};
//	}
}
