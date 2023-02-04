package com.sinprl.binq.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.sinprl.binq.R;
import com.sinprl.binq.adaptors.ReasonGridAdaptor;

import java.util.ArrayList;
import java.util.List;

public class Reason_Display_Add extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reason_display_add);
        showreason();
    }

    private void showreason() {



        final RecyclerView reason_recycle_view = findViewById(R.id.list_reasons);
        final GridLayoutManager reasonLayoutManager = new GridLayoutManager(this,3);
        reason_recycle_view.setLayoutManager(reasonLayoutManager);

        List<String > reasons = new ArrayList<>();
        reasons.add("High Feaver");
        reasons.add("Cough");
        reasons.add("Temperature");
        reasons.add("Dizzeness");
        reasons.add("Weakness");
        reasons.add("Other");

        ReasonGridAdaptor reasonGridAdaptor = new ReasonGridAdaptor(Reason_Display_Add.this,reasons);
        reason_recycle_view.setAdapter(reasonGridAdaptor);
    }
}