package sn.executors.actorsexec;

import akka.actor.UntypedActor;

/**
 * Created by Sumanth on 15/10/14.
 */
public class MapActor extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof String){
            System.out.println("rcvd message  " +  message);
        }
    }

}
