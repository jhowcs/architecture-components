package br.com.jhowcs.roomexample;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import br.com.jhowcs.roomexample.repository.local.User;

public class MainActivity extends LifecycleActivity
        implements View.OnClickListener, UserRegisterDialog.OnUserRegistered {

    private static final String TAG = "MainActivity";

    private FloatingActionButton fab;

    private UserRegisterDialog registerDialog;

    private final String tagRegisterDialog = "tagRegisterDialog";

    private RecyclerView rvUser;
    private UserAdapter adapter;

    private  UserViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fabUserRegister);
        rvUser = findViewById(R.id.rvUser);

        fab.setOnClickListener(this);

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        viewModel.init();

        setupAdapter();

    }

    private void setupAdapter() {
        adapter = new UserAdapter();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        adapter.setUserList(viewModel.getAllUser());
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

    @Override
    public void callback(User user) {
        adapter.addNewUserRegistered(user);
    }
}
