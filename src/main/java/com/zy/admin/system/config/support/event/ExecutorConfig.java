package com.zy.admin.system.config.support.event;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
public class ExecutorConfig extends AsyncConfigurerSupport {

	@Override
	@Bean(name = "zyTaskExecutor")
	public Executor getAsyncExecutor() {
		// TODO Auto-generated method stub
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(50);
		executor.setQueueCapacity(10000);
		executor.setKeepAliveSeconds(3300);
		executor.setThreadNamePrefix("zy-executor-");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		// TODO Auto-generated method stub
		return new SimpleAsyncUncaughtExceptionHandler();
	}
	
}
