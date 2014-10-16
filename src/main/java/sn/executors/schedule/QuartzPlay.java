package sn.executors.schedule;


import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.*;

import static org.quartz.JobBuilder.*;
/**
 * Created by Sumanth on 15/10/14.
 */
public class QuartzPlay {

    public static class HelloJob implements Job {

        public HelloJob() {
        }

        public void execute(JobExecutionContext context)
                throws JobExecutionException
        {
            System.err.println("Hello!  HelloJob is executing." + new Date().toString());
        }
    }

    public static void main( String[] args ) throws SchedulerException{



        Scheduler scheduler = new StdSchedulerFactory().getScheduler();



        //for(int i=0;i < 10;i++){
            JobDetail job = newJob(HelloJob.class)
                    .withIdentity("myJob" + 11, "group1") // name "myJob", group "group1"
                    .build();
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger" + 11, "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                    .build();
            CronTrigger trigger2 = TriggerBuilder.newTrigger()
                    .withIdentity("10secJob" +11, "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
                    .build();

            Map<JobDetail,Set<Trigger>> jobScheduleInfo = new HashMap<JobDetail, Set<Trigger>>();
            Set<Trigger> jobTriggers = new HashSet<Trigger>();
            jobTriggers.add(trigger);
            jobTriggers.add(trigger2);


            scheduler.scheduleJob(job,jobTriggers,true);
            scheduler.getListenerManager().addJobListener(new JobPostProcessor());

        //}


        scheduler.start();




    }
}
