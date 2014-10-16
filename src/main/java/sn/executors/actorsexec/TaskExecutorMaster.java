package sn.executors.actorsexec;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.pattern.Patterns;
import scala.concurrent.duration.Duration;
import sn.executors.TaskState;
import sn.executors.task.TaskCompletionStatus;

import java.util.concurrent.TimeUnit;

/**
 * Created by Sumanth on 15/10/14.
 */
public class TaskExecutorMaster extends UntypedActor {
    //keep sending message to self to check if there are
    //tasks to be further progressed and worked out

    //Example: HTTP case where schedule the actor and keep it pinging it every 5 mins
    //to check for the completion
    //to start send message to it

    private ActorRef workerRef;
    boolean reactOnTick = false;
    @Override
    public void preStart(){
        getContext().system().scheduler().scheduleOnce( Duration.create(5, TimeUnit.SECONDS),
                getSelf(),"tick",getContext().dispatcher(),null);
    }

    @Override
    public void postRestart(Throwable reason){}

    @Override
    public void onReceive(Object msg) throws Exception {

        if (msg instanceof String){
            if (msg.equals("tick")){
                if (reactOnTick){
                    System.out.println("React on tick now");
                    workerRef.tell(TaskCommand.GET_PROGRESS,self());

                }
                //send another tick in future
                getContext().system().scheduler().scheduleOnce( Duration.create(5, TimeUnit.SECONDS),
                        getSelf(),"tick",getContext().dispatcher(),null);
                System.out.println("Tick came in, put back in another on");
            }else if (msg.equals("Begin")){

                System.out.println("Being mayhem !!!, kick of actual stuff");
                 workerRef = getContext().actorOf(Props.create(HttpTaskRunner.class),"HTTPTaskActor");

                workerRef.tell(TaskCommand.RUN,self());
            }
        }else if (msg instanceof TaskStateMsg){
            System.out.println("Obtained " + msg + " from worker");
            if ((TaskStateMsg)msg == TaskStateMsg.SUCCESS){
                System.out.println("Finally obtained " + msg +  "from Http worker");
                reactOnTick=false;
            }

            //Patterns.ask()

        }

    }

    private void reactOnTick(){


    }



    public static void main(String [] args){
        ActorSystem _system = ActorSystem.create("TaskExecMaster");
        ActorRef cordRef = _system.actorOf(Props.create(TaskExecutorMaster.class));
        cordRef.tell("Begin",null);

        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * http://www.chrisstucchio.com/blog/2013/actors_vs_futures.html
     *
     * http://stackoverflow.com/questions/19944034/blocking-calls-in-akka-actors
     * import akka.actor.{Props, ActorSystem, ActorRef, Actor}
     import com.typesafe.config.ConfigFactory
     import java.text.SimpleDateFormat
     import java.util.Date
     import scala.concurrent._
     import ExecutionContext.Implicits.global

     object Sample {

     private val fmt = new SimpleDateFormat("HH:mm:ss:SSS")

     def printWithTime(msg: String) = {
     println(fmt.format(new Date()) + " " + msg)
     }

     class WorkerActor extends Actor {
     protected def receive = {
     case "now" =>
     val commander = sender
     printWithTime("worker: got command")
     future {
     printWithTime("worker: started")
     Thread.sleep(1000)
     printWithTime("worker: done")
     }(ExecutionContext.Implicits.global) onSuccess {
     // here commander = original sender who requested the start of the future
     case _ => commander ! "done"
     }
     commander ! "working"
     case "alive?" =>
     printWithTime("worker: alive")
     }
     }

     class ManagerActor(worker: ActorRef) extends Actor {
     protected def receive = {
     case "do" =>
     worker ! "now"
     printWithTime("manager: flush sent")
     case "working" =>
     printWithTime("manager: resource allocated")
     case "done" =>
     printWithTime("manager: work is done")
     case "alive?" =>
     printWithTime("manager alive")
     worker ! "alive?"
     }
     }

     def main(args: Array[String]) {

     val config = ConfigFactory.parseString("" +
     "akka.loglevel=DEBUG\n" +
     "akka.debug.lifecycle=on\n" +
     "akka.debug.receive=on\n" +
     "akka.debug.event-stream=on\n" +
     "akka.debug.unhandled=on\n" +
     ""
     )

     val system = ActorSystem("mine", config)
     val actor1 = system.actorOf(Props[WorkerActor], "worker")
     val actor2 = system.actorOf(Props(new ManagerActor(actor1)), "manager")

     actor2 ! "do"
     actor2 ! "alive?"
     actor2 ! "alive?"
     actor2 ! "alive?"

     printWithTime("Humming ...")
     Thread.sleep(5000)
     printWithTime("Shutdown!")
     system.shutdown()

     }
     }
     */
}
