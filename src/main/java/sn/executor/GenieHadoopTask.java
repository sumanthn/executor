package sn.executor;

import akka.actor.UntypedActor;

/**
 * Created by Sumanth on 16/10/14.
 */
public class GenieHadoopTask extends UntypedActor {

    String statusCheckUrl;
    String cancelUrl;

    int count =0;

    @Override
    public void onReceive(Object msg) throws Exception {

        if (msg instanceof TaskCommand){


            switch((TaskCommand)msg){

                case INIT:
                    //make it ready
                    break;
                case RUN:


                case SUBMIT:

                    //submit for execution
                    System.out.println("GenieHadoopTask:Submitted task " );
                    //check if success

                    getContext().sender().tell(TaskResponseMsg.SUCCESS,self());

                    break;
                case STATUS:
                    //check status and report back

                    System.out.println("GenieHadoopTask:Check status for " );
                    //check for URL status and if completed say SUCCESS



                    count ++;
                    if (count > 5) {
                        System.out.println("GenieHadoopTask:Run successful");
                        getContext().sender().tell(TaskResponseMsg.SUCCESS,self());
                    }else{
                        getContext().sender().tell(TaskResponseMsg.RUNNING,self());
                    }
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
        }

    }
}
