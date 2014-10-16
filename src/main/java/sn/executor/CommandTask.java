package sn.executor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Future;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sumanth on 16/10/14.
 */
public class CommandTask extends UntypedActor {

    int count=0;
    Patterns patterns;
    Future<Object> promise;
    ActorRef commandExecutor;
    @Override
    public void onReceive(Object msg) throws Exception {


        ProcessBuilder pb = new ProcessBuilder();
        if (msg instanceof TaskCommand){


            switch((TaskCommand)msg){

                case INIT:
                    //make it ready
                    break;
                case RUN:


                case SUBMIT:

                    //submit for execution
                    System.out.println("CommandTask:Submitted task " );
                    //check if success
                    commandExecutor = getContext().actorOf(Props.create(CommandExecutor.class),"CommandExecutor");


                    promise =Patterns.ask(commandExecutor, "Execute",
                            Timeout.apply(5, TimeUnit.SECONDS));



                    //getContext().sender().tell(TaskResponseMsg.SUCCESS, self());

                    break;
                case STATUS:
                    //check status and report back

                    System.out.println("CommandTask:Check status for " );
                    //check for URL status and if completed say SUCCESS
                    boolean result = promise.isCompleted();
                    System.out.println("Promise is " + result);
                    if (result== false){
                        System.out.println("Checking for results");
                       //getContext().sender().tell(TaskResponseMsg.RUNNING,self());




                    }else{
                        System.out.println("Checkng resul, promise is completed");
                    }

                    /*
                    count ++;
                    if (count > 5) {
                        System.out.println("GenieHadoopTask:Run successful");
                        getContext().sender().tell(TaskResponseMsg.SUCCESS,self());
                    }else{
                        getContext().sender().tell(TaskResponseMsg.RUNNING,self());
                    }*/
                    break;
                case CANCEL:
                    //invoke cancel URL
                    System.out.println("invoking cancel on task");
                    break;
                case DESTROY:
                    System.out.println("Invoking DESTTORY");

                    getContext().stop(self());
                    break;
            }
        }else if (msg instanceof String){
            System.out.println("Rcvd message from command worker");
        }

    }

    public static void main(String [] args){

        ActorSystem _system = ActorSystem.create("TaskCommander");
        ActorRef cordRef = _system.actorOf(Props.create(CommandTask.class));

        cordRef.tell(TaskCommand.SUBMIT,null);

        for(int i=0;i < 10;i++) {
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cordRef.tell(TaskCommand.STATUS, null);
        }
        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
