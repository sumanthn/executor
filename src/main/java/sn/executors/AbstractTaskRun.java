package sn.executors;

import sn.executors.task.TaskRun;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sumanth on 14/10/14.
 */
public abstract class AbstractTaskRun implements TaskRun {

    protected  ProcessBuilder processBuilder;

    protected int timeOut;
    protected  TaskRunStatus runStatus;
    public void run(){


        try {

            Process proc = processBuilder.start();
            boolean successExec = proc.waitFor(2,TimeUnit.SECONDS);
            System.out.println(successExec);
            if (!successExec) {

                System.out.println("Trying to kill it for now");


                if (proc.isAlive())
                    proc.destroyForcibly();

                runStatus = TaskRunStatus.TIMEOUT;

            }else{
                int exitVal = proc.exitValue();
                System.out.println("Exit status as " + exitVal);

                if (exitVal!=0)
                    runStatus = TaskRunStatus.ERROR;
                else
                    runStatus = TaskRunStatus.SUCCESS;

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }catch (IllegalThreadStateException e2){
            e2.printStackTrace();

        }finally {
            //close streams optionally
        }

       // return TaskRunStatus.ERROR;
    }
}
