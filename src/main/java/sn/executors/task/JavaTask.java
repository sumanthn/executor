package sn.executors.task;

import sn.executors.TaskState;

/**
 * take care of all class loading and other issue
 * Created by Sumanth on 14/10/14.
 */
public class JavaTask extends AbstractCommandTask {
    @Override
    public int getId() {
        return id;
    }

    @Override
    protected void prepareProcess() {

    }

    @Override
    public boolean run() {
        return false;
    }

    @Override
    public boolean cancel() {
        return true;
    }

    @Override
    public boolean isAlive() {
        return true;
    }


}
