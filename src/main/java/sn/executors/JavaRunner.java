package sn.executors;

/**
 * Created by Sumanth on 14/10/14.
 */
public class JavaRunner extends AbstractTaskRun {

    @Override
    public TaskRunStatus getRunStatus() {
        return null;
    }

    public  JavaRunner(){
        ProcessBuilder procBuilder = new ProcessBuilder("/tmp/runner.sh");
        procBuilder.inheritIO();

        this.processBuilder = procBuilder;
    }

    public static void main(String [] args){
        JavaRunner javaRunner = new JavaRunner();
        javaRunner.run();

    }
}
