package sn.executors.task;

import sn.executors.TaskRunStatus;

/**
 * Created by Sumanth on 14/10/14.
 */
public interface TaskRun  {

    public TaskRunStatus getRunStatus();

    public void run();

}
