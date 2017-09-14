package br.com.jhowcs.roomexample;

import android.arch.lifecycle.LiveData;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.jhowcs.roomexample.repository.local.User;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LiveData<List<User>> userList;

    public void setUserList(LiveData<List<User>> userList) {
        this.userList = userList;
    }

    public void addNewUserRegistered(User user) {
        userList.getValue().add(user);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        User user = userList.getValue().get(position);

        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.txtName.setText(user.getFirstName());
        viewHolder.txtLastName.setText(user.getLastName());
    }

    @Override
    public int getItemCount() {
        if (userList != null) {
            return userList.getValue().size();
        }

        return 0;
    }

    static final class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private TextView txtLastName;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtLastName = itemView.findViewById(R.id.txtLastName);
        }
    }
}
