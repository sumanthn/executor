package sn.executors.actorsexec;

import akka.actor.Actor;
import akka.actor.UntypedActor;

/**
 * Created by Sumanth on 15/10/14.
 */
public class WorkerActor extends UntypedActor {
    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof String){
            System.out.println("Worker actor is working now");

        }
    }
}
