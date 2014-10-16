package sn.executors.taskrunner;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import scala.concurrent.duration.Duration;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sumanth on 16/10/14.
 */
public class JobCoordinator extends UntypedActor {

    private ActorRef workerTaskRef;
    boolean reactOnTick = false;
    private JobState jobState;
    private List<Task> tasks;


    @Override
    public void preStart(){
        getContext().system().scheduler().scheduleOnce( Duration.create(5, TimeUnit.SECONDS),
                getSelf(),new TickMsg(),getContext().dispatcher(),null);
    }





    @Override
    public void onReceive(Object msg) throws Exception {

        //switches are much better if used scala

        //Tick msgs keep me on toes
        if (msg instanceof TickMsg){

            if (reactOnTick){
                //this is diff for different tasks scheduled

                System.out.println("React on tick message");

            }

            getContext().system().scheduler().scheduleOnce( Duration.create(5, TimeUnit.SECONDS),
                    getSelf(),new TickMsg(),getContext().dispatcher(),null);

        }else if (msg instanceof TaskStateMsg){




        }else if (msg instanceof  JobMsg) {

            System.out.println("ready to kick start job");
            jobState = JobState.INITIALIZING;
            //schedule the first task


        }else{
            System.out.println("Message " + msg + " is not handled");
        }


    }

    private void initJob(){

    }
}
