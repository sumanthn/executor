package sn.executor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.pattern.Patterns;
import com.sun.javafx.tk.Toolkit;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by Sumanth on 16/10/14.
 */
public class TaskCoordinator extends UntypedActor {

    private ActorRef workerTaskRef;
    boolean reactOnTick = false;
    private TaskState curState;

    @Override
    public void preStart(){
        getContext().system().scheduler().scheduleOnce( Duration.create(5, TimeUnit.SECONDS),
                getSelf(),new TickMsg(),getContext().dispatcher(),null);
    }

    @Override
    public void onReceive(Object msg) throws Exception {


        //receives 3 kinds of messages

        if (msg instanceof TickMsg){

            if (reactOnTick){

                if (curState == TaskState.RUNNING){
                    //check for progress
                    workerTaskRef.tell(TaskCommand.STATUS,self());
                }

            }

            getContext().system().scheduler().scheduleOnce( Duration.create(5, TimeUnit.SECONDS),
                    getSelf(),new TickMsg(),getContext().dispatcher(),null);


        }else if (msg instanceof  TaskResponseMsg){


            switch((TaskResponseMsg)msg){


                case SUCCESS:
                    if (curState == TaskState.SUBMITTED){

                        System.out.println("TaskCordinator:Submitted Genie Hadoop Task");
                        curState = TaskState.RUNNING;


                    }else if (curState == TaskState.RUNNING){

                        System.out.println("Task has completed running");
                        cleanupTask();
                    }
                    break;
                case ERROR:
                    if (curState == TaskState.SUBMITTED){

                        System.out.println("Submission error , retry");

                    }else if (curState == TaskState.RUNNING){
                        System.out.println("Task has failed ");
                        cleanupTask();

                    }
                    break;
                case TIMEOUT:
                    System.out.println("Task timed out");
                    cleanupTask();
                    break;
                case RUNNING:

                        System.out.println("TaskCoordinator:Task is running");
                    break;
                case FAILED:
                    cleanupTask();
                    System.out.println("Task failed to execute");
                    break;
            }


        }else if (msg instanceof String){


        }else if (msg instanceof TaskCommand){

            if (msg == TaskCommand.RUN){
                System.out.println("Running task now ");

                /*
                workerTaskRef = getContext().actorOf(Props.create(GenieHadoopTask.class),"GenieHadoopTask");
                curState = TaskState.SUBMITTED;

                workerTaskRef.tell(TaskCommand.SUBMIT,self());
                reactOnTick=true;
*/


            }


        }





    }

    private void submitCommandTask(){
        workerTaskRef = getContext().actorOf(Props.create(CommandTask.class),"CommandTask");


    }
    private void submitGenieHadoopTask(){
        workerTaskRef = getContext().actorOf(Props.create(GenieHadoopTask.class),"GenieHadoopTask");
        curState = TaskState.SUBMITTED;

        workerTaskRef.tell(TaskCommand.SUBMIT,self());
        reactOnTick=true;
    }


    private void cleanupTask(){
        curState = TaskState.SUCCESS;
        workerTaskRef.tell(TaskCommand.DESTROY,self());
        reactOnTick=false;

    }
    public static void main(String [] args){

        ActorSystem _system = ActorSystem.create("TaskExecMaster");
        ActorRef cordRef = _system.actorOf(Props.create(TaskCoordinator.class));

        cordRef.tell(TaskCommand.RUN,null);

        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
