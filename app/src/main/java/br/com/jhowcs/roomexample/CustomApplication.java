package br.com.jhowcs.roomexample;

import android.app.Application;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import br.com.jhowcs.roomexample.repository.local.DatabaseProvider;


public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseProvider.init(this);

        int availableProcessors = Runtime.getRuntime().availableProcessors();
        AppExecutor.init(availableProcessors, availableProcessors * 2, 0,
                TimeUnit.SECONDS, new LinkedBlockingDeque());
    }
}
