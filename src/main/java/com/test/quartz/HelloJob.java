package com.test.quartz;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.StatefulJob;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class HelloJob implements StatefulJob {

	public static void main(String[] args) throws IOException {
		try {
			// 获取Scheduler实例
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();

			// 具体任务
			JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("job1", "group1").build();

			// 触发时间点
			// SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).repeatForever();
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/1 * * * * ?");
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow().withSchedule(scheduleBuilder).build();

			// 交由Scheduler安排触发
			scheduler.scheduleJob(job, trigger);

			/* 为观察程序运行，此设置主程序睡眠3分钟才继续往下运行（因下一个步骤是“关闭Scheduler”） */
			try {
				TimeUnit.MINUTES.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// 关闭Scheduler
			scheduler.shutdown();

		} catch (SchedulerException se) {
			se.printStackTrace();
		}
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			System.out.println("============开始执行定时任务============" + Thread.currentThread().getName());
			Thread.sleep(5000);
			System.out.println("============定时任务继续执行================" + Thread.currentThread().getName());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}