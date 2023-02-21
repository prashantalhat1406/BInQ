package com.sinprl.binq.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sinprl.binq.R;
import com.sinprl.binq.dataclasses.Appointment;
import com.sinprl.binq.dataclasses.User;
import com.sinprl.binq.intefaces.OnItemClickListener;

import java.util.List;

public class UserListAdaptor extends RecyclerView.Adapter<UserListAdaptor.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater layoutInflater;
    private final List<User> users;
    private final OnItemClickListener mOnItemClickListener;

    public UserListAdaptor(Context mContext, List<User> users, OnItemClickListener mOnItemClickListener) {
        this.mContext = mContext;
        layoutInflater = LayoutInflater.from(mContext);
        this.users = users;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_user, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user  = users.get(position);
        holder.user_name.setText("  "+user.getName());
        holder.phone.setText("  "+user.getPhone());
        holder.itemView.setOnClickListener(view -> mOnItemClickListener.onItemClick(view,position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView user_name;
        public final TextView phone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_name =  itemView.findViewById(R.id.txt_user_item_user_name);
            phone =  itemView.findViewById(R.id.txt_user_item_phone);
        }
    }
}
