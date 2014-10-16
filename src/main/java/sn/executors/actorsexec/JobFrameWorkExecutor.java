package sn.executors.actorsexec;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.duration.Duration;
import scala.concurrent.impl.Future;

import java.util.concurrent.TimeUnit;

/**
 * Created by Sumanth on 15/10/14.
 */
public class JobFrameWorkExecutor {

    public static void main(String [] args){


        ActorSystem _system = ActorSystem.create("MapReduceApp");
        ActorRef cordRef = _system.actorOf(Props.create(JobCordinatorActor.class));

        //ask the blocking caller command to respond within timeout
       scala.concurrent.Future<Object> obj = Patterns.ask(cordRef, "MSG", Timeout.apply(10, TimeUnit.SECONDS));
        System.out.println("worker now");

        /*for(int i=0;i < 5* 10 * 1000;i++){
            ActorRef ref = _system.actorOf(Props.create(JobCordinatorActor.class));
            ref.tell("Hi There " + i,null);
        }*/


        try {
            Thread.sleep(30*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("actors created " + AkkaCounters.getInstance().getCounter());
        AkkaCounters.getInstance().getCounter();
        _system.awaitTermination(Duration.apply(30, TimeUnit.MINUTES));

    }
}
