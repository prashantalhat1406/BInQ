package com.sinprl.binq.adaptors;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sinprl.binq.R;
import com.sinprl.binq.dataclasses.Appointment;
import com.sinprl.binq.intefaces.OnItemClickListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AppointmentDetailsAdaptor extends RecyclerView.Adapter<AppointmentDetailsAdaptor.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater layoutInflater;
    private final List<Appointment> appointments;

    private final OnItemClickListener mOnItemClickListener;

    public AppointmentDetailsAdaptor(Context mContext, List<Appointment> appointments, OnItemClickListener mOnItemClickListener) {
        this.mContext = mContext;
        layoutInflater = LayoutInflater.from(mContext);
        this.appointments = appointments;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_appointment_details_queue, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Appointment appointment = appointments.get(position);

        holder.reason.setText(appointment.getReason());
        if (appointment.getActive() == 0){
            if(appointment.getPaymentmethod() == 1)
                holder.amount.setText(" "+appointment.getAmount() + " (cash)");
            else
                holder.amount.setText(" "+appointment.getAmount() + " (online)");

            holder.treatment.setText(" "+ appointment.getTreatment());

            holder.followupdate.setText(" "+appointment.getFollowupdate() );
        }else{
            holder.amount.setVisibility(View.GONE);
            holder.treatment.setVisibility(View.GONE);
            holder.treatment_title.setVisibility(View.GONE);
            holder.followupdate.setVisibility(View.GONE);
            holder.followupdate_label.setVisibility(View.GONE);
        }



        DateFormat source_dateformat = new SimpleDateFormat("yyyyddMM", Locale.US);
        DateFormat target_dateformat = new SimpleDateFormat("ddMMMyyyy", Locale.US);

        try {
            String d = target_dateformat.format(source_dateformat.parse(appointment.getDate_of_appointment()));
            holder.appointmentdate.setText(" "+d );


        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

//        switch (appointment.getActive()){
//            case 1 : holder.status.setText(R.string.appointment_active);holder.status.setTextColor(Color.BLUE);break;
//            case 0 : holder.status.setText(R.string.appointment_cancel);holder.status.setTextColor(Color.RED);break;
//            case 2 : holder.status.setText(R.string.appointment_done);holder.status.setTextColor(Color.GREEN);break;
//        }

        switch (appointment.getActive()){
            case 1 : holder.card.setBackgroundResource(R.drawable.appointment_active); break;
            case 0 : holder.card.setBackgroundResource(R.drawable.appointment_done); break;
            case 2 : holder.card.setBackgroundResource(R.drawable.appointment_cancel); break;
        }

        holder.itemView.setOnClickListener(view -> mOnItemClickListener.onItemClick(view,position));

    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{



        //public final TextView status;

        public final TextView appointmentdate;
        public final TextView followupdate;
        public final TextView followupdate_label;
        public final TextView treatment;
        public final TextView treatment_title;
        public final TextView reason;

        public final TextView amount;

        public final ConstraintLayout card;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //status =  itemView.findViewById(R.id.txt_appointment_details_status);
            reason =  itemView.findViewById(R.id.txt_appointment_details_reason);
            treatment =  itemView.findViewById(R.id.txt_appointment_details_treatment);
            treatment_title =  itemView.findViewById(R.id.txt_appointment_details_treatment_title);
            appointmentdate =  itemView.findViewById(R.id.txt_appointment_details_appointmentdate);
            amount =  itemView.findViewById(R.id.txt_appointment_details_amount);
            followupdate =  itemView.findViewById(R.id.txt_appointment_details_followupdate);
            followupdate_label =  itemView.findViewById(R.id.txt_appointment_details_followupdate_label);
            card = itemView.findViewById(R.id.appointment_details_card);
        }
    }

}
