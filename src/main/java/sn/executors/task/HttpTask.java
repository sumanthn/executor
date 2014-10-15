package sn.executors.task;

import sn.executors.TaskState;

/**
 * Created by Sumanth on 14/10/14.
 */
public class HttpTask extends AbstractRunnableTask {
    @Override
    public int getId() {
        return 0;
    }

    @Override
    public boolean run() {
        return false;
    }

    @Override
    public boolean cancel() {
        return false;
    }

    @Override
    public boolean isAlive() {
        return false;
    }


}
