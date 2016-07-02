package com.mirror.mirrortry.net;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dllo on 16/7/1.
 */
public class ThreadSingleton {
    private static ThreadSingleton ourInstance;
    private ExecutorService executorService;

    public static ThreadSingleton getInstance() {
        if (ourInstance == null) {
            synchronized (VolleySingleton.class) {
                if (ourInstance == null) {
                    ourInstance = new ThreadSingleton();
                }
            }
        }
        return ourInstance;
    }

    private ThreadSingleton() {
        executorService = Executors.newSingleThreadExecutor();
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }
}
