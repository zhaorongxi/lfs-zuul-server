package com.lfs.zuul;

import com.lfs.annotation.LfsApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@LfsApplication("com.lfs.*")
@MapperScan("com.lfs.**.dao")
public class LfsZuulServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LfsZuulServerApplication.class, args);
	}

}
