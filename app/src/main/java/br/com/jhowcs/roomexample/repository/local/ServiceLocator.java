package br.com.jhowcs.roomexample.repository.local;

import br.com.jhowcs.roomexample.repository.local.user.UserDao;
import br.com.jhowcs.roomexample.repository.local.user.UserRepository;

public class ServiceLocator {

    public static UserRepository getRepository() {
        UserDao userDao = DatabaseProvider.getDatabase().userDao();
        return new UserRepository(userDao);
    }
}
