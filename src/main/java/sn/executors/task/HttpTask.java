package sn.executors.task;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import sn.executors.TaskState;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Sumanth on 14/10/14.
 */
public class HttpTask extends AbstractRunnableTask {

    private String url;
    private String host;
    private String port;
    private String service;
    //Should this be a map
    private String params;


    public HttpTask(String host, String port, String service) {
        this.host = host;
        this.port = port;
        this.service = service;
    }

    protected  void prepareHttpParams(){
        //TODO: change to support protocol
        url = "http://"+host+":"+port+"/"+service;

        taskState = TaskState.READY;
    }
    @Override
    public int getId() {
        return 0;
    }

    @Override
    public boolean run() {



        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {

            System.out.println("Executing request " + url);

            HttpGet httpget = new HttpGet("http://localhost:8080/genie/v0/config/cluster");

            httpget.setHeader("Accept","application/json");

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    System.out.println("Stat code " +  status);
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            String responseBody = httpclient.execute(httpget, responseHandler);
           // writeToFile();
            //System.out.println("----------------------------------------");
            System.out.println(responseBody);

            taskState = TaskState.RUNNING;

            int count=0;
            while(true){
                Thread.sleep(1*1000);
                //keep checking for the response from site
                count++;
                if (count == 60) break;;
            }

            System.out.println("Response rcvd " );
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    @Override
    public boolean cancel() {
        return false;
    }

    @Override
    public boolean isAlive() {
        return false;
    }



    public static void main(String [] args){

        for (int i=0;i < 2;i++) {
            try {
                Thread.sleep(2*100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpTask httpTask = new HttpTask("localhost", "8080", "genie/v0/config/cluster");
                    httpTask.prepareHttpParams();
                    httpTask.run();
                }
            }).start();

        }

        while(true){
            try {
                Thread.sleep(10*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
