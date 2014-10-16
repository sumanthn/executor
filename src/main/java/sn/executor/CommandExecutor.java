package sn.executor;

import akka.actor.UntypedActor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sumanth on 16/10/14.
 */
public class CommandExecutor extends UntypedActor {
    @Override
    public void onReceive(Object msg) throws Exception {

        if (msg instanceof String){
            System.out.println("CommandExecutor:Execute command now");
            runCommand();


            System.out.println("Completed command run, now return");
            getContext().sender().tell("Completed",self());

        }
    }

    private void runCommand(){
        ProcessBuilder procBuilder = new ProcessBuilder("python", "/Users/Sumanth/scripts/tst1.py");
        procBuilder.inheritIO();

        try {

            Process proc = procBuilder.start();
            System.out.println("Launched command");
            boolean successExec = proc.waitFor(300, TimeUnit.SECONDS);
            System.out.println(successExec);
            if (successExec) {
                int exitVal = proc.exitValue();
                System.out.println("Exit status as " + exitVal);


            }else{

                System.out.println("Trying to kill it for now");

                proc.destroyForcibly();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }catch (IllegalThreadStateException e2){
            e2.printStackTrace();

        }
    }
}
