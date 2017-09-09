package br.com.jhowcs.roomexample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UserRegisterDialog extends DialogFragment implements View.OnClickListener {

    private static final String TAG = "UserRegisterDialog";

    private EditText edtName;
    private EditText edtLastName;
    private Button btnSave;

    private int id;

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2,
            0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    private UserDao mDao;

    private OnUserRegistered listener;

    public interface OnUserRegistered {
        void callback(User user);
    }

    public static UserRegisterDialog newInstance(OnUserRegistered listener) {
        UserRegisterDialog dialog = new UserRegisterDialog();
        dialog.listener = listener;

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_register_dialog, container, true);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        edtName = view.findViewById(R.id.edtName);
        edtLastName = view.findViewById(R.id.edtLastName);
        btnSave = view.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(this);
        setCancelable(false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CustomApplication customApplication = (CustomApplication) getActivity().getApplication();
        mDao = customApplication.getDatabase().userDao();

        threadPoolExecutor.execute(queryMaxId());
        threadPoolExecutor.execute(queryFromDatabase());
    }

    @Override
    public void onClick(View view) {
        User user = new User();
        user.setId(id++);
        user.setFirstName(edtName.getText().toString());
        user.setLastName(edtLastName.getText().toString());

        threadPoolExecutor.execute(saveOnDatabase(user));

        if (listener != null) {
            listener.callback(user);
        }

        dismiss();
    }

    private Runnable saveOnDatabase(final User user) {
        return new Runnable() {
            @Override
            public void run() {
                mDao.insert(user);
            }
        };
    }

    private Runnable queryFromDatabase() {
        return new Runnable() {
            @Override
            public void run() {
                List<User> userList = mDao.getAll();
                for (User user: userList) {
                    Log.d(TAG, "run: " + user);
                }
            }
        };
    }

    private Runnable queryMaxId() {
        return new Runnable() {
            @Override
            public void run() {
                id = mDao.maxId() + 1;
            }
        };
    }
}
