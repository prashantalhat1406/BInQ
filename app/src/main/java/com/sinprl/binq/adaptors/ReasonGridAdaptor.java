package com.sinprl.binq.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sinprl.binq.R;

import java.util.List;

public class ReasonGridAdaptor extends RecyclerView.Adapter<ReasonGridAdaptor.ViewHolder>  {

    private final Context mcContext;
    private final LayoutInflater layoutInflater;
    private final List<String> reasons;

    public ReasonGridAdaptor(Context mcContext, List<String> reasons) {
        this.mcContext = mcContext;
        this.layoutInflater = LayoutInflater.from(mcContext);
        this.reasons = reasons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_reason, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.reason.setText(reasons.get(position));

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
