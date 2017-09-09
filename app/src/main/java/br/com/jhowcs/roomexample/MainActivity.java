package br.com.jhowcs.roomexample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import br.com.jhowcs.roomexample.repository.local.DatabaseProvider;
import br.com.jhowcs.roomexample.repository.local.User;
import br.com.jhowcs.roomexample.repository.local.UserDao;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, UserRegisterDialog.OnUserRegistered {

    private static final String TAG = "MainActivity";

    private FloatingActionButton fab;

    private UserRegisterDialog registerDialog;

    private final String tagRegisterDialog = "tagRegisterDialog";

    private RecyclerView rvUser;
    private UserAdapter adapter;

    private UserDao mDao;

    ThreadPoolExecutor executor = new ThreadPoolExecutor(2,2,0, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fabUserRegister);
        rvUser = findViewById(R.id.rvUser);

        fab.setOnClickListener(this);

        setupAdapter();

        mDao = DatabaseProvider.getDatabase().userDao();
        executor.execute(getUserList());
    }

    private void setupAdapter() {
        adapter = new UserAdapter();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvUser.setAdapter(adapter);
        rvUser.setLayoutManager(llm);
    }

    @Override
    public void onClick(View view) {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentByTag(tagRegisterDialog) == null) {
            registerDialog = UserRegisterDialog.newInstance(this);
            registerDialog.show(fm, tagRegisterDialog);
        }
    }

    private Runnable getUserList() {
        return new Runnable() {
            @Override
            public void run() {
                adapter.setUserList(mDao.getAll());
                adapter.notifyDataSetChanged();
            }
        };
    }

    @Override
    public void callback(User user) {
        adapter.addNewUserRegistered(user);
    }
}
