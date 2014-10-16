package sn.executors.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * Created by Sumanth on 15/10/14.
 */
public class JobPostProcessor implements JobListener {
    @Override
    public String getName() {
        return "PostProcessor";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
        

    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {

    }

    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {

        System.out.println(jobExecutionContext.getJobDetail().getKey());
    }
}
