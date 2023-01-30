package com.sinprl.binq.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sinprl.binq.R;
import com.sinprl.binq.dataclasses.Fruits;

import java.util.List;

public class FruitListAdaptor extends RecyclerView.Adapter<FruitListAdaptor.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater layoutInflater;
    private final List<Fruits> fruits;

    public FruitListAdaptor(Context mContext, List<Fruits> fruits) {
        this.mContext = mContext;
        layoutInflater = LayoutInflater.from(mContext);
        this.fruits = fruits;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.listitem, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fruits fruit = fruits.get(position);
        holder.fruitname.setText(fruit.getName());
        holder.fruitinformation.setText(fruit.getInformation());
    }

    @Override
    public int getItemCount() {
        return fruits.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView fruitname;
        public final TextView fruitinformation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fruitname = (TextView) itemView.findViewById(R.id.txtname);
            fruitinformation = (TextView) itemView.findViewById(R.id.txtinfo);
        }
    }

}
