package sn.executors.task;

import sn.executors.TaskState;
import sn.executors.task.AbstractCommandTask;
import sn.executors.task.TaskCompletionStatus;
import sn.executors.task.UserParams;

/**
 * Created by Sumanth on 14/10/14.
 */
public class ScriptTask extends AbstractCommandTask {

    private UserParams userParams;
    //user params can be map KVs
    //a list separated by spaces
    //simply a string
    private final String command;
    public ScriptTask(final String command){
        this.command = command;
    }

    @Override
    protected void prepareProcess() {
        pb = new ProcessBuilder();
        pb.inheritIO();
        taskState = TaskState.READY;

    }

    public ScriptTask(String command,final UserParams userParams){

        this.command = command;
        this.userParams = userParams;

    }

    @Override
    public int getId() {
        return id;
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
        return true;
    }


}
