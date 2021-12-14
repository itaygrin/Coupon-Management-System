package com.project.couponsys;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.project.couponsys.facade.LoginFacade;

@SpringBootApplication
@EnableAsync
public class CouponsysApplication {
	
	public static void main(String[] args) {

		ApplicationContext context = 
		SpringApplication.run(CouponsysApplication.class, args);
		
		Job job = context.getBean(Job.class);
		
		
		try {
			Thread trd = new Thread (job);
			trd.start();		//CHECK JOB
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		LoginFacade loginFacade = (LoginFacade) context.getBean("loginFacade");
		loginFacade.login();
		//LoginFacade.login();
	}
	
	@Bean
	public Executor taskExecutor() {
	    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	    executor.setCorePoolSize(2);
	    executor.setMaxPoolSize(2);
	    executor.setQueueCapacity(500);
	    executor.setThreadNamePrefix("GithubLookup-");
	    executor.initialize();
	    return executor;
	}
		
	
	
	


}
