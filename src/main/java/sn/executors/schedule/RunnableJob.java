package sn.executors.schedule;

import org.quartz.*;
import sn.executors.TaskState;
import sn.executors.task.RunnableTask;
import sn.executors.task.TaskCompletionStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sumanth on 15/10/14.
 */
public class RunnableJob implements Job, InterruptableJob {


    private JobDetail jobDetail;
    private List<RunnableTask> tasks;
    private float progressPct;
    //execution state
    private List<TaskState> taskState;
    private boolean killBeforeCompletion;

    //completion state
    private List<TaskCompletionStatus> completionStatus;

    public RunnableJob(final List<RunnableTask> tasks){
        if (tasks!=null){
            taskState = new ArrayList<TaskState>();
            completionStatus = new ArrayList<TaskCompletionStatus>();
            for(RunnableTask task : tasks){
                //Modify this when it is really READY ,take care of the dependency
                taskState.add(TaskState.READY);
                completionStatus.add(TaskCompletionStatus.NOT_RUN);

            }
        }
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {



        //this can be actor system but for now keep it simple
        //assume its sequential
        for(int i =0;i < tasks.size();i++){

            //a small change in here will make the
            //tasks to allow partial runs
            if (!killBeforeCompletion) {
                if (taskState.get(i) != TaskState.COMPLETED) {
                    taskState.add(i, TaskState.RUNNING);

                    tasks.get(i).run();
                }
            }
        }

    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        killBeforeCompletion=true;
    }
}
