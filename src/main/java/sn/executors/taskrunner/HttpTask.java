package sn.executors.taskrunner;

import org.apache.http.client.utils.URIBuilder;

/**
 * Created by Sumanth on 16/10/14.
 */
public class HttpTask implements Task {
    private final String protocol;

    private final String host;
    private final int port;
    private final String service;

    private final HttpOperation httpOperation;

    private String url;

    public HttpTask(String protocol, String host, int port, String service, HttpOperation httpOperation) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.service = service;
        this.httpOperation = httpOperation;
        URIBuilder builder = new URIBuilder()
                .setScheme(protocol)
                .setHost(host).setPort(port)
                .setPath(service);
        url = builder.toString();

    }

    public String getProtocol() {


        return protocol;
    }

    public String getUrl() {
        return url;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getService() {
        return service;
    }

    public HttpOperation getHttpOperation() {
        return httpOperation;
    }

    public static void main(String[] args) {

        HttpTask task = new HttpTask("http","localhost",8080,"/genie/v0/config/cluster/",HttpOperation.GET);
        System.out.println(task.getUrl());

    }
}
