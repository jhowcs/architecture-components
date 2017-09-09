package br.com.jhowcs.roomexample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<User> userList;

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void addNewUserRegistered(User user) {
        userList.add(user);
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
        User user = userList.get(position);

        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.txtName.setText(user.getFirstName());
        viewHolder.txtLastName.setText(user.getLastName());
    }

    @Override
    public int getItemCount() {
        return userList.size();
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