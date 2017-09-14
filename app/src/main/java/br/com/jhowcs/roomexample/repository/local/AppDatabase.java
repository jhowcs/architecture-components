package br.com.jhowcs.roomexample.repository.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import br.com.jhowcs.roomexample.repository.local.user.User;
import br.com.jhowcs.roomexample.repository.local.user.UserDao;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
}
