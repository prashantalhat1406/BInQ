package com.sinprl.binq.adaptors;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sinprl.binq.R;
import com.sinprl.binq.dataclasses.TimeSlots;
import com.sinprl.binq.intefaces.OnItemClickListener;

import java.util.List;

public class TimeSlotGridAdaptor extends RecyclerView.Adapter<TimeSlotGridAdaptor.ViewHolder>  {

    private final Context mcContext;
    private final LayoutInflater layoutInflater;
    private final List<TimeSlots> timeSlots;

    private OnItemClickListener mOnItemClickListener;

    public TimeSlotGridAdaptor(Context mcContext, List<TimeSlots> timeSlots, OnItemClickListener mOnItemClickListener) {
        this.mcContext = mcContext;
        this.layoutInflater = LayoutInflater.from(mcContext);
        this.timeSlots = timeSlots;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_time, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.timeslot.setText(timeSlots.get(position).getTimeslot());


        if (!timeSlots.get(position).getAvailable()){
            holder.timeslot_card.setBackground(ContextCompat.getDrawable(mcContext, R.drawable.red_border_rectangle));
            /*holder.timeslot_card.setCardElevation(4);
            holder.timeslot_card.setRadius(4);*/
            holder.timeslot_card.setEnabled(false);

        }else {
            holder.timeslot_card.setBackground(ContextCompat.getDrawable(mcContext, R.drawable.green_border_rectangle));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view,position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return timeSlots.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView timeslot;
        public final CardView timeslot_card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeslot = itemView.findViewById(R.id.txt_time_item);
            timeslot_card = itemView.findViewById(R.id.timeslot_card);
        }
    }
}
