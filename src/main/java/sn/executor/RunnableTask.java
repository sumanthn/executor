package sn.taskrunner;

import akka.actor.UntypedActor;
import sn.executor.TaskResponseMsg;
import sn.executor.TaskState;

/**
 * Created by Sumanth on 17/10/14.
 */
public abstract class RunnableTask extends UntypedActor{

    //though it is sen
    protected TaskState curState;

    public void notifyParent(final TaskResponseMsg responseMsg){
        getContext().sender().tell(responseMsg,self());

    }

    protected void setCurrentState(final TaskState taskState){


    }



}
