package sn.executors;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sumanth on 14/10/14.
 */
public class ProcessRunner {


    public static void main(String [] args){


            ProcessBuilder procBuilder = new ProcessBuilder("python", "/Users/Sumanth/scripts/tst1.py");
            procBuilder.inheritIO();

            try {

                Process proc = procBuilder.start();
                boolean successExec = proc.waitFor(30,TimeUnit.SECONDS);
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
