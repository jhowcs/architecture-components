package br.com.jhowcs.roomexample;

import android.app.Application;

import br.com.jhowcs.roomexample.repository.local.DatabaseProvider;


public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseProvider.init(this);
    }
}
