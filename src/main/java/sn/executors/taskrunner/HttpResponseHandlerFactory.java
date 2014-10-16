package sn.executors.taskrunner;

/**
 * Created by Sumanth on 16/10/14.
 */
public class HttpResponseHandlerFactory {
    private static HttpResponseHandlerFactory ourInstance = new HttpResponseHandlerFactory();

    public static HttpResponseHandlerFactory getInstance() {
        return ourInstance;
    }

    private HttpResponseHandlerFactory() {
    }


    public HttpResponseParser getParser(final ServiceTaskType serviceTaskType){

        switch (serviceTaskType){
            case GENIE_REST:
                return new GenieTaskResponseParser();
        }

        throw new UnsupportedOperationException("Other services not implemented");

    }
}
