package sn.executors.actorsexec;

import akka.actor.Scheduler;
import akka.actor.UntypedActor;

/**
 * Created by Sumanth on 15/10/14.
 * Use case of HTTP task runner is to invoke a REST service in Genie
 * to schedule a Hadoop Job; which can take long time to complete
 * during this period, ensure that the same actor can be used to
 * run more jobs; so no more blocking for hours to check status
 */
public class HttpTaskRunner extends UntypedActor {


    int counter =0;


    @Override
    public void onReceive(Object msg) throws Exception {

        if (msg instanceof TaskCommand){
            System.out.println("Command to " + msg);

            switch ((TaskCommand)msg){

                case RUN:

                    run();
                    getSender().tell(TaskStateMsg.SUCCESS,self());
                    break;
                case SUSPEND:

                    //invoke suspend API and send out msg
                    getSender().tell(TaskStateMsg.CANCEL,self());
                    break;
                case CANCEL:
                    getSender().tell(TaskStateMsg.CANCEL,self());
                    break;
                case GET_PROGRESS:
                    //check and proceed
                    System.out.println("Asking for progress");
                    if (counter >5 ) {getSender().tell(TaskStateMsg.SUCCESS,self()); }
                    else counter++;
                    break;
            }
            /*Scheduler scheduler = getContext().system().scheduler();
            timeoutMessage = scheduler.scheduleOnce(askParam.timeout.duration(),
                    self(), new AskTimeout(), context().dispatcher(), null);*/
           // akkaSystem.scheduler.scheduleOnce(5 seconds, actor, "msgFoo")
        }
        unhandled(msg);

    }

    private boolean run(){

        System.out.println("Invoked the rest api");

        return true;

    }
}
