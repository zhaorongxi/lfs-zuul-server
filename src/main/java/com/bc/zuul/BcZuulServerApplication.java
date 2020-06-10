package com.bc.zuul;

import com.bc.annotation.BcApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@BcApplication("com.bc.*")
@MapperScan("com.bc.**.dao")
public class BcZuulServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BcZuulServerApplication.class, args);
	}

}
