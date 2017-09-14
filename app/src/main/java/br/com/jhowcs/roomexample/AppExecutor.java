package br.com.jhowcs.roomexample;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AppExecutor {

    private static ThreadPoolExecutor executor;

    public static void init(final int corePoolSize, final int maximunPoolSize,
                            final int keepAliveTime, final TimeUnit unit,
                            BlockingDeque blockingDeque) {
        executor = new ThreadPoolExecutor(corePoolSize, maximunPoolSize, keepAliveTime,
                unit, blockingDeque);
    }

    public static ThreadPoolExecutor provideExecutor() {
        return executor;
    }
}
