package br.com.jhowcs.roomexample.repository.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id = :id")
    List<User> getById(int id);

    @Query("SELECT * FROM User WHERE first_name LIKE :names OR last_name LIKE :names ")
    List<User> getByNames(String names);

    @Query("SELECT MAX(id) FROM user")
    int maxId();

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);
}
