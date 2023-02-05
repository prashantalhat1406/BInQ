package com.sinprl.binq.adaptors;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sinprl.binq.R;
import com.sinprl.binq.intefaces.OnItemClickListener;

import java.util.List;

public class ReasonGridAdaptor extends RecyclerView.Adapter<ReasonGridAdaptor.ViewHolder>  {

    private final Context mcContext;
    private final LayoutInflater layoutInflater;
    private final List<String> reasons;

    private OnItemClickListener mOnItemClickListener;

    public ReasonGridAdaptor(Context mcContext, List<String> reasons,OnItemClickListener mOnItemClickListener) {
        this.mcContext = mcContext;
        this.layoutInflater = LayoutInflater.from(mcContext);
        this.reasons = reasons;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_reason, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.reason.setText(reasons.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(view,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return reasons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView reason;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reason = itemView.findViewById(R.id.txt_reason_item);
        }
    }
}
