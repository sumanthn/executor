package sn.executors.task;

import sn.executors.TaskState;

/**
 * Created by Sumanth on 14/10/14.
 */
public interface RunnableTask {

    public int getId();

    public boolean run();
    public boolean cancel();

    public boolean isAlive();
    public TaskCompletionStatus getCompletionStatus();
    public TaskState getTaskState();

}
