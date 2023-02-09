package com.sinprl.binq.pages.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinprl.binq.R;
import com.sinprl.binq.adaptors.ReasonGridAdaptor;
import com.sinprl.binq.dataclasses.Reason;
import com.sinprl.binq.intefaces.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;

public class Reason_Display_Add extends AppCompatActivity implements OnItemClickListener {

    List<Reason> reasons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reason_display_add);

        populateReasons();
    }




    @Override
    public void onItemClick(View view, int position) {
        Intent reasonIntent = new Intent();
        reasonIntent.putExtra("reason", reasons.get(position).getReason_text());
        setResult(Reason_Display_Add.RESULT_OK, reasonIntent);
        finish();
    }

    private void populateReasons() {

        final RecyclerView reason_recycle_view = findViewById(R.id.list_reasons);
        final GridLayoutManager reasonLayoutManager = new GridLayoutManager(this,3);
        reason_recycle_view.setLayoutManager(reasonLayoutManager);

        reasons = new ArrayList<>();
        reasons.add(new Reason("Other"));

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://binq-1171a-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference databaseReference = database.getReference("Reasons/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reasons.clear();
                for (DataSnapshot s : snapshot.getChildren()){
                    Log.d("Test", s.toString());
                    Reason f = s.getValue(Reason.class);
                    reasons.add(f);
                }
                ReasonGridAdaptor reasonGridAdaptor = new ReasonGridAdaptor(Reason_Display_Add.this,reasons, Reason_Display_Add.this);
                reason_recycle_view.setAdapter(reasonGridAdaptor);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }
}