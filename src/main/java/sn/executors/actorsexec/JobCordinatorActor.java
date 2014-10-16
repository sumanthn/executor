package sn.executors.actorsexec;

import akka.actor.UntypedActor;

/**
 * Created by Sumanth on 15/10/14.
 */
public class JobCordinatorActor extends UntypedActor {
    @Override
    public void preStart(){
        //System.out.println("Called pre start");
    }
    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String){
          //  System.out.println("rcvd message  " +  message + " " + Thread.currentThread().getId() );

            AkkaCounters.getInstance().update();
            getContext().stop(self());
        }
    }

   @Override
    public void postStop(){

       //System.out.println("called poststop");
   }
}

