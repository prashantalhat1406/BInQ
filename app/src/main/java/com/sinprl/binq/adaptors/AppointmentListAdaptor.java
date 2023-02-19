package com.sinprl.binq.adaptors;

import android.annotation.SuppressLint;
import android.content.Context;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.sinprl.binq.R;
import com.sinprl.binq.dataclasses.Appointment;
import com.sinprl.binq.intefaces.OnItemClickListener;
import java.util.List;

public class AppointmentListAdaptor extends RecyclerView.Adapter<AppointmentListAdaptor.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater layoutInflater;
    private final List<Appointment> appointments;

    private boolean is_Admin;

    private final OnItemClickListener mOnItemClickListener;

    public AppointmentListAdaptor(Context mContext, List<Appointment> appointments, OnItemClickListener mOnItemClickListener, boolean is_Admin) {
        this.mContext = mContext;
        layoutInflater = LayoutInflater.from(mContext);
        this.appointments = appointments;
        this.mOnItemClickListener = mOnItemClickListener;
        this.is_Admin = is_Admin;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View itemView = layoutInflater.inflate(R.layout.item_appointment_queue, parent, false);
        View itemView = layoutInflater.inflate(R.layout.item_appointment_queue_redesigned, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Appointment appointment = appointments.get(position);
        holder.token.setText(appointment.getToken());
        holder.user_name.setText(appointment.getUser_name());
        holder.time.setText("  " + appointment.getTime());
        holder.reason.setText(appointment.getReason());
        holder.phone.setText("  "+appointment.getPhone());

        if(!is_Admin)
            holder.action_done.setVisibility(View.GONE);

        switch (appointment.getActive()){
            case 1 : holder.status.setText(R.string.appointment_active);holder.status.setTextColor(Color.BLUE);break;
            case 0 : holder.status.setText(R.string.appointment_cancel);holder.status.setTextColor(Color.RED);break;
            case 2 : holder.status.setText(R.string.appointment_done);holder.status.setTextColor(Color.GREEN);break;
        }

        if (appointment.getActive() != 1){
            holder.action_done.setVisibility(View.GONE);
            holder.action_cancel.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(view -> mOnItemClickListener.onItemClick(view,position));

    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView token;

        public final TextView status;
        public final TextView user_name;
        public final TextView time;
        public final TextView reason;
        public final TextView phone;

        public final TextView action_done;
        public final TextView action_cancel;

        public final CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            token =  itemView.findViewById(R.id.txt_apt_item_token);
            status =  itemView.findViewById(R.id.txt_appointment_item_status);
            user_name =  itemView.findViewById(R.id.txt_apt_item_user_name);
            time =  itemView.findViewById(R.id.txt_apt_item_time);
            reason =  itemView.findViewById(R.id.txt_apt_item_reason);
            phone =  itemView.findViewById(R.id.txt_apt_item_phone);
            card = itemView.findViewById(R.id.lay_appt_list_item);
            action_done = itemView.findViewById(R.id.txt_appointment_item_done);
            action_cancel = itemView.findViewById(R.id.txt_appointment_item_cancel);
        }
    }

}
