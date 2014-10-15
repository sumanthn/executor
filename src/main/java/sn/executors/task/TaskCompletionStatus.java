package sn.executors.task;

/**
 * Created by Sumanth on 14/10/14.
 */
public enum  TaskCompletionStatus {
    NOT_RUN,
    SUCCESS,
    ERROR,
    TIMEDOUT,
    UNKNOWN;

    @Override
    public String toString() {

        switch (this){

            case NOT_RUN:
                return "Notrun";
            case SUCCESS:
                return "Sucess";
            case ERROR:
                return "Error";
            case TIMEDOUT:
                return "Timeout";

        }
        return "Unknown";



    }
}
