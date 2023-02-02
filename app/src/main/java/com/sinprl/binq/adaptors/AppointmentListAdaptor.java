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

import java.util.List;

public class AppointmentListAdaptor extends RecyclerView.Adapter<AppointmentListAdaptor.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater layoutInflater;
    private final List<Appointment> appointments;

    public AppointmentListAdaptor(Context mContext, List<Appointment> appointments) {
        this.mContext = mContext;
        layoutInflater = LayoutInflater.from(mContext);
        this.appointments = appointments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_appointment_queue, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);
        holder.token.setText(appointment.getToken());
        holder.user_name.setText(appointment.getUser_name());
        holder.time.setText(appointment.getTime());
        holder.reason.setText(appointment.getReason());
        holder.phone.setText(appointment.getPhone().toString());

    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView token;
        public final TextView user_name;
        public final TextView time;
        public final TextView reason;
        public final TextView phone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            token = (TextView) itemView.findViewById(R.id.txt_apt_item_token);
            user_name = (TextView) itemView.findViewById(R.id.txt_apt_item_user_name);
            time = (TextView) itemView.findViewById(R.id.txt_apt_item_time);
            reason = (TextView) itemView.findViewById(R.id.txt_apt_item_reason);
            phone = (TextView) itemView.findViewById(R.id.txt_apt_item_phone);
        }
    }

}
