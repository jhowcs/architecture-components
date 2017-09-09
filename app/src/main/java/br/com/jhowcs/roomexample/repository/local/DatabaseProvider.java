package br.com.jhowcs.roomexample.repository.local;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseProvider {
    public static final String databaseName = "teste";

    private static AppDatabase database;

    private DatabaseProvider() {}

    public static void init(Context context) {
        database = Room.databaseBuilder(context, AppDatabase.class, databaseName).build();
    }

    public static AppDatabase getDatabase() {
        return database;
    }
}
