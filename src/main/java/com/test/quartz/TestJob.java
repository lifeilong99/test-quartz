package com.test.quartz;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestJob {
	public void execute() {
		try {
			System.out.println("============开始执行定时任务============" + Thread.currentThread().getName());
			Thread.sleep(5000);
			System.out.println("============定时任务继续执行============" + Thread.currentThread().getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
		System.in.read();
	}
}