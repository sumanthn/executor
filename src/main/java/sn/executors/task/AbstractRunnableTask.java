package sn.executors.task;

import sn.executors.TaskState;

import java.util.Random;

/**
 * Created by Sumanth on 14/10/14.
 */
public abstract class AbstractRunnableTask implements RunnableTask{

    //is it thread safe may be not
    static Random randomizer = new Random();

    protected boolean isAlive;
    protected int id;
    protected TaskState taskState;
    protected TaskCompletionStatus taskCompletionStatus;
    protected int timeout;


    protected AbstractRunnableTask() {
        this.id = randomizer.nextInt();
        taskCompletionStatus = TaskCompletionStatus.NOT_RUN;
    }



    @Override
    public TaskCompletionStatus getCompletionStatus() {
        return null;
    }

    @Override
    public TaskState getTaskState() {
        return null;
    }


}
