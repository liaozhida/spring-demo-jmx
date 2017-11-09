package com.example.jmx.mbean;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;



/**
 * @author zhidaliao
 *	
 * The StopWatch is registered as a Spring bean, and then exposed as a JMX MBean.
 * 
 * objectName 是可选的，用于指定命名空间
 */
@Component
@ManagedResource
(objectName="com.example.jmx.mbean:name=StopWatch")
public class StopWatchImp implements InitializingBean, StopWatch {


	private final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();

	private volatile Future<?> future;

	private final AtomicInteger seconds = new AtomicInteger();

	/* （非 Javadoc）
	 * @see com.example.springjmx.StopWatch#getSeconds()
	 */
	@Override
	@ManagedAttribute
	public int getSeconds() {
		return this.seconds.get();
	}

	public void afterPropertiesSet() {
		this.start();
	}

	/* （非 Javadoc）
	 * @see com.example.springjmx.StopWatch#start()
	 */
	@Override
	@ManagedOperation
	public void start() {
		this.scheduler.initialize();
		this.future = this.scheduler.scheduleAtFixedRate(new Runnable() {
			public void run() {
				seconds.incrementAndGet();
				System.out.println("主程序在计数: "+ seconds);
			}
		}, 1000);
	}

	/* （非 Javadoc）
	 * @see com.example.springjmx.StopWatch#stop()
	 */
	@Override
	@ManagedOperation
	public void stop() {
		this.future.cancel(true);
	}

	/* （非 Javadoc）
	 * @see com.example.springjmx.StopWatch#reset()
	 */
	@Override
	@ManagedOperation
	public void reset() {
		this.seconds.set(0);
	}


}
