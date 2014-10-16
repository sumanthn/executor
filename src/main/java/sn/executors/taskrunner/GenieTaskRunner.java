package sn.executors.taskrunner;

import akka.actor.UntypedActor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import sn.executors.task.*;

import java.io.IOException;

/**
 * Runs Http execute operation to Genie
 * <p/>
 * <p/>
 * Created by Sumanth on 16/10/14.
 */
public class GenieTaskRunner extends UntypedActor {


    private String statusUrl;


    @Override
    public void onReceive(Object msg) throws Exception {

        //submit job

        if (msg instanceof TaskCommand) {
            if ((TaskCommand) msg == TaskCommand.RUN) {

                HttpResponseParser responseParser =
                        HttpResponseHandlerFactory.getInstance().getParser(ServiceTaskType.GENIE_REST);
               // int status = response.getStatusLine().getStatusCode();

                /*if (status >= 200 && status < 300) {
                    //success
                    taskState = TaskState.RUNNING;


                } else {
                    taskState = TaskState.ERROR;

                }*/

               //get the run URL and execute

                HttpResponse response=
                        executeHttpRequest("http://localhost:8080/genie/v0/config/cluster/",HttpOperation.GET);
                if (response!=null){

                    System.out.println("Executed and got response " +  response.getEntity().toString());


                }

            } else if ((TaskCommand) msg == TaskCommand.GET_PROGRESS) {

            } else if ((TaskCommand) msg == TaskCommand.CANCEL) {

            } else if ((TaskCommand) msg == TaskCommand.SUSPEND) {

            }
        }


    }

    private HttpResponse executeHttpRequest(final String url, final HttpOperation httpOperation) {


        CloseableHttpClient httpclient = HttpClients.createDefault();
        if (httpOperation == HttpOperation.GET) {

            HttpGet httpget = new HttpGet(url);
            httpget.setHeader("Accept","application/json");

            try {
                //blocking call
                return httpclient.execute(httpget);
                //supply a factory to parse and handle responses


            } catch (IOException e) {
                e.printStackTrace();
            }


        } else if (httpOperation == HttpOperation.POST) {


        } else if (httpOperation == HttpOperation.DELETE) {


        }
        try {
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
