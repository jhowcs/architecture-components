package br.com.jhowcs.roomexample;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import br.com.jhowcs.roomexample.repository.local.AppDatabase;
import br.com.jhowcs.roomexample.repository.local.user.User;
import br.com.jhowcs.roomexample.repository.local.user.UserDao;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UserDaoTest {

    AppDatabase database;

    @Before
    public void setup() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase.class).build();
    }

    @After
    public void tearDown() {
        database.close();
    }

    @Test
    public void whenCallInsertMethod_ShouldStoreDataInDatabase() {
        User user = new User();
        user.setId(1);
        user.setFirstName("Jonathan");
        user.setLastName("Campos");

        database.userDao().insert(user);

        List<User> userList = database.userDao().getAll();
        assertThat(userList.size(), is(1));
    }

    @Test
    public void whenCallInsertMethodToInsertFiftyUser_ShouldRetrieveAllFiftyUsersInserted() {
        List<User> userToBeInserted = getStaticList(50);
        insertUserList(userToBeInserted);

        List<User> userFromDatabase = database.userDao().getAll();

        assertThat(userFromDatabase.size(), is(userToBeInserted.size()));
    }

    @Test
    public void whenQueryUserByNameAndLastName_ShouldRetrieveSpecificUser() {
        UserDao userDao = database.userDao();

        List<User> userToBeInserted = getStaticList(5);
        insertUserList(userToBeInserted);

        final String nameToQuery = "name 4";
        List<User> userWithNameFromDatabase = userDao.getByNames(nameToQuery);
        assertThat(1, is(userWithNameFromDatabase.size()));

        final String lastNameToQuery = "last name 4";
        List<User> userWithLastNameFromDatabase = userDao.getByNames(lastNameToQuery);
        assertThat(1, is(userWithLastNameFromDatabase.size()));
    }

    @Test
    public void whenQueryUserById_ShouldRetrieveSpecificUser() {
        UserDao userDao = database.userDao();

        User user1 = new User();
        user1.setId(1);
        user1.setFirstName("Érica");
        user1.setLastName("Lourenço");

        User user2 = new User();
        user2.setId(2);
        user2.setFirstName("Jonathan");
        user2.setLastName("Campos");

        User user3 = new User();
        user3.setId(3);
        user3.setFirstName("Ivete");
        user3.setLastName("Fernandes");

        userDao.insert(user1);
        userDao.insert(user2);
        userDao.insert(user3);

        User userWithIdThree = userDao.getById(3);
        assertThat(userWithIdThree.getId(), is(user3.getId()));
        assertThat(userWithIdThree.getFirstName(), is(user3.getFirstName()));
        assertThat(userWithIdThree.getLastName(), is(user3.getLastName()));
    }

    private void insertUserList(List<User> users) {
        int cont = 0;
        UserDao userDao = database.userDao();
        while (cont < users.size()) {
            userDao.insert(users.get(cont));
            cont++;
        }
    }

    private List<User> getStaticList(final int numberToInsert) {
        int cont = 0;
        List<User> userList = new ArrayList<>(numberToInsert);
        while (cont++ < numberToInsert) {
            User user = new User();
            user.setId(cont);
            user.setFirstName("name " + cont);
            user.setLastName("last name " + cont);
            userList.add(user);
        }

        return userList;
    }
}