package sn.executors.actorsexec;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Sumanth on 15/10/14.
 */
public class AkkaCounters {
    private static AkkaCounters ourInstance = new AkkaCounters();


    public static AkkaCounters getInstance() {
        return ourInstance;
    }

    private AkkaCounters() {
    }

    private AtomicLong counter = new AtomicLong();

    public void update(){
        counter.getAndIncrement();
    }

    public long getCounter(){
        return counter.get();
    }
}
