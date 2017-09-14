package br.com.jhowcs.roomexample.repository.local.user;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import br.com.jhowcs.roomexample.AppExecutor;

public class UserRepository {

    private UserDao dao;

    public UserRepository(UserDao dao) {
        this.dao = dao;
    }

    public LiveData<List<User>> fetchAll() {
        final MutableLiveData<List<User>> users = new MutableLiveData<>();

        AppExecutor.provideExecutor().execute(new Runnable() {
            @Override
            public void run() {
                users.postValue(dao.getAll());
            }
        });

        return users;
    }
}
