package com.web.flowershopping;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.web.flowershopping.manager.Mapper")
public class FlowershoppingApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowershoppingApplication.class, args);
	}

}
