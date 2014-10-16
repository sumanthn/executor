package sn.executors.taskrunner;

import org.apache.http.HttpResponse;

/**
 * Created by Sumanth on 16/10/14.
 */
public interface HttpResponseParser {

    public int getHttpCode();
    public void parseResponse(final HttpResponse httpResponse);
}
