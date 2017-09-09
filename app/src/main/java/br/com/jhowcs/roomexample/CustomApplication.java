package br.com.jhowcs.roomexample;

import android.app.Application;
import android.arch.persistence.room.Room;


public class CustomApplication extends Application {

    public static final String databaseName = "teste";

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(this, AppDatabase.class, databaseName).build();
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
