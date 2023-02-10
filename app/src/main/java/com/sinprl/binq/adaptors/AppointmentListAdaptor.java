package com.sinprl.binq.adaptors;

import android.annotation.SuppressLint;
import android.content.Context;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import androidx.recyclerview.widget.RecyclerView;

import com.sinprl.binq.R;
import com.sinprl.binq.dataclasses.Appointment;
import com.sinprl.binq.enums.AppointmentStatus;
import com.sinprl.binq.intefaces.OnItemClickListener;

import java.util.List;

public class AppointmentListAdaptor extends RecyclerView.Adapter<AppointmentListAdaptor.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater layoutInflater;
    private final List<Appointment> appointments;

    private OnItemClickListener mOnItemClickListener;

    public AppointmentListAdaptor(Context mContext, List<Appointment> appointments, OnItemClickListener mOnItemClickListener) {
        this.mContext = mContext;
        layoutInflater = LayoutInflater.from(mContext);
        this.appointments = appointments;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_appointment_queue, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Appointment appointment = appointments.get(position);
        holder.token.setText(appointment.getToken());
        holder.user_name.setText(appointment.getUser_name());
        holder.time.setText(appointment.getTime());
        holder.reason.setText(appointment.getReason());
        holder.phone.setText(appointment.getPhone());

        switch (appointment.getActive()){
            case 1 : holder.status.setText("active");holder.status.setTextColor(Color.BLUE);break;
            case 0 : holder.status.setText("cancelled");holder.status.setTextColor(Color.RED);break;
            case 2 : holder.status.setText("done");holder.status.setTextColor(Color.GREEN);break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(view,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView token;

        public final TextView status;
        public final TextView user_name;
        public final TextView time;
        public final TextView reason;
        public final TextView phone;

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
        }
    }

}
