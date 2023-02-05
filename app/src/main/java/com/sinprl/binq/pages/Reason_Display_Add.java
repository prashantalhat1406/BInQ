package com.sinprl.binq.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.sinprl.binq.R;
import com.sinprl.binq.adaptors.ReasonGridAdaptor;
import com.sinprl.binq.intefaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class Reason_Display_Add extends AppCompatActivity implements OnItemClickListener {

    List<String> reasons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reason_display_add);

        reasons = new ArrayList<>();
        reasons.add("High Feaver");
        reasons.add("Cough");
        reasons.add("Temperature");
        reasons.add("Dizzeness");
        reasons.add("Weakness");
        reasons.add("Other");



        showreason();

    }



    private void showreason() {



        final RecyclerView reason_recycle_view = findViewById(R.id.list_reasons);
        final GridLayoutManager reasonLayoutManager = new GridLayoutManager(this,3);
        reason_recycle_view.setLayoutManager(reasonLayoutManager);



        ReasonGridAdaptor reasonGridAdaptor = new ReasonGridAdaptor(Reason_Display_Add.this,reasons, Reason_Display_Add.this);
        reason_recycle_view.setAdapter(reasonGridAdaptor);

    }


    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(view.getContext(),  reasons.get(position) + "", Toast.LENGTH_SHORT).show();

        Intent reasonIntent = new Intent();
        reasonIntent.putExtra("reason", reasons.get(position));
        setResult(Reason_Display_Add.RESULT_OK, reasonIntent);
        finish();
    }
}