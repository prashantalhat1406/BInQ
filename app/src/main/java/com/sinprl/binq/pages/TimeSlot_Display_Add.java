package com.sinprl.binq.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sinprl.binq.R;
import com.sinprl.binq.adaptors.TimeSlotGridAdaptor;
import com.sinprl.binq.dataclasses.TimeSlots;
import com.sinprl.binq.intefaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class TimeSlot_Display_Add extends AppCompatActivity implements OnItemClickListener {

    List<TimeSlots> timeslots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_display_add);

        timeslots = new ArrayList<>();
        timeslots.add(new TimeSlots("10:00 am", true));
        timeslots.add(new TimeSlots("11:00 am", false));
        timeslots.add(new TimeSlots("12:00 am", true));

        showtimeslots();
    }

    private void showtimeslots() {

        final RecyclerView timeslot_recycle_view = findViewById(R.id.list_timeslots);
        final GridLayoutManager timeslotLayoutManager = new GridLayoutManager(this,3);
        timeslot_recycle_view.setLayoutManager(timeslotLayoutManager);



        TimeSlotGridAdaptor timeslotGridAdaptor = new TimeSlotGridAdaptor(TimeSlot_Display_Add.this,timeslots, TimeSlot_Display_Add.this);
        timeslot_recycle_view.setAdapter(timeslotGridAdaptor);
    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(view.getContext(),  reasons.get(position) + "", Toast.LENGTH_SHORT).show();

        Intent reasonIntent = new Intent();
        reasonIntent.putExtra("timeslot", timeslots.get(position).getTimeslot());
        setResult(Reason_Display_Add.RESULT_OK, reasonIntent);
        finish();
    }
}