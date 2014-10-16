package sn.executors.task;

import sn.executors.TaskState;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sumanth on 14/10/14.
 */
public abstract class AbstractCommandTask extends AbstractRunnableTask {

    protected Process process;
    protected  ProcessBuilder pb;
    protected AbstractCommandTask(){

    }

    protected abstract void prepareProcess();
    public boolean run() {

        if (taskState!= TaskState.READY) {
            try {

                taskState = taskState.RUNNING;
                Process process = pb.start();
                boolean successExec = process.waitFor(timeout, TimeUnit.SECONDS);
                System.out.println(successExec);
                if (!successExec) {

                    System.out.println("Trying to kill it for now");


                    if (process.isAlive())
                        process.destroyForcibly();

                    taskCompletionStatus = TaskCompletionStatus.TIMEDOUT;

                } else {
                    int exitVal = process.exitValue();
                    System.out.println("Exit status as " + exitVal);

                    if (exitVal != 0)
                        taskCompletionStatus = TaskCompletionStatus.ERROR;
                    else
                        taskCompletionStatus = TaskCompletionStatus.SUCCESS;

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            } catch (IllegalThreadStateException e2) {
                e2.printStackTrace();

            } finally {
                //close streams optionally
            }

            // return TaskRunStatus.ERROR;
            taskState = TaskState.COMPLETED;
        }

        return true;
    }


}
