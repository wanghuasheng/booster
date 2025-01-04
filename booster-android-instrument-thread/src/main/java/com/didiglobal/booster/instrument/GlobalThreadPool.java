package com.didiglobal.booster.instrument;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GlobalThreadPool {
    private static final int MAX_POOL_SIZE = 50;
    private static final long KEEP_ALIVE_TIME = 10L;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
            1, // corePoolSize
            MAX_POOL_SIZE,
            KEEP_ALIVE_TIME,
            TIME_UNIT,
            new LinkedBlockingQueue<>(),
            new NamedThreadFactory("global-pool"),
            new ThreadPoolExecutor.AbortPolicy()
    );

    static {
        EXECUTOR.allowCoreThreadTimeOut(true);
    }

    public static void execute(Runnable command) {
        EXECUTOR.execute(command);
    }

    public static Future<?> submit(Runnable task) {
        return EXECUTOR.submit(task);
    }

    public static <T> Future<T> submit(Callable<T> task) {
        return EXECUTOR.submit(task);
    }
}