package sn.taskrunner;

import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by Sumanth on 17/10/14.
 */
public class TaskCoordinator extends RunnableTask {

    private LoggingAdapter logger = Logging.getLogger(getContext().system(), this);



    @Override
    public void onReceive(Object msg) throws Exception {



    }


    public static void main(String [] args){



    }
}
