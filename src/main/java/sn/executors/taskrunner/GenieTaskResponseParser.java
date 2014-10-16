package sn.executors.taskrunner;

import org.apache.http.HttpResponse;

/**
 * Created by Sumanth on 16/10/14.
 */
public class GenieTaskResponseParser implements HttpResponseParser {

    @Override
    public int getHttpCode() {
        return 0;
    }

    @Override
    public void parseResponse(HttpResponse httpResponse) {

       System.out.println("Parsing Http Response");
    }
}
