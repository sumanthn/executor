package sn.taskrunner;

/**
 * Created by Sumanth on 17/10/14.
 */
public class TaskInstance {

    private int id;
    private TaskType taskType;

    private TaskParameters taskParameters;

    public TaskInstance(int id, TaskType taskType) {
        this.id = id;
        this.taskType = taskType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public TaskParameters getTaskParameters() {
        return taskParameters;
    }

    public void setTaskParameters(TaskParameters taskParameters) {
        this.taskParameters = taskParameters;
    }

    public static void main(String [] args){

        TaskInstance taskInstance = new TaskInstance(10, TaskType.GENIE_MR);
        final HttpServiceParams serviceParams = new HttpServiceParams("http", "localhost", 8080, "/genie/v0/jobs");
        GenieMRTaskParameters mrTaskParameters = new GenieMRTaskParameters(serviceParams, "Sumanth", "hadoop",
                "jar /tmp/hdptest.jar cruncher.TxnOperations /txndatain /txnout5",
                "file:///tmp/hdptest.jar",10,3);
        taskInstance.setTaskParameters(mrTaskParameters);
    }
}
