package br.com.jhowcs.roomexample.presentation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import br.com.jhowcs.roomexample.repository.local.ServiceLocator;
import br.com.jhowcs.roomexample.repository.local.user.User;
import br.com.jhowcs.roomexample.repository.local.user.UserRepository;

public class UserViewModel extends ViewModel {

    private UserRepository userRepository;
    private LiveData<List<User>> usersList;

    public UserViewModel() {
        this.userRepository = ServiceLocator.getRepository();
    }

    public void init() {
        if (usersList == null) {
            usersList = userRepository.fetchAll();
        }
    }

    public LiveData<List<User>> getAllUser() {
        return usersList;
    }
}
