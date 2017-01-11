package com.beeva.iiibeevaafterwork.data.network;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class NetworkTask<V> extends FutureTask<V> {

    public NetworkTask(Callable<V> callable) {
        super(callable);
    }

    public NetworkTask(Runnable runnable, V result) {
        super(runnable, result);
    }
}
