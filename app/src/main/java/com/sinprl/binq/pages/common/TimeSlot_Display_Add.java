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
import com.sinprl.binq.adaptors.TimeSlotGridAdaptor;
import com.sinprl.binq.constants.Constants;
import com.sinprl.binq.dataclasses.TimeSlots;
import com.sinprl.binq.intefaces.OnItemClickListener;
import com.sinprl.binq.utils.Utils;
import com.sinprl.binq.utils.comparators.TimeComparator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TimeSlot_Display_Add extends AppCompatActivity implements OnItemClickListener {

    List<TimeSlots> timeslots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_display_add);

        populateTimeslots();
    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(view.getContext(),  reasons.get(position) + "", Toast.LENGTH_SHORT).show();

        Intent reasonIntent = new Intent();
        reasonIntent.putExtra("timeslot", timeslots.get(position).getTimeslot());
        reasonIntent.putExtra("no_available_appointments", timeslots.get(position).getNo_of_appointments());
        setResult(Reason_Display_Add.RESULT_OK, reasonIntent);
        finish();
    }



    private void populateTimeslots() {

        final RecyclerView timeslot_recycle_view = findViewById(R.id.list_timeslots);
        final GridLayoutManager timeslotLayoutManager = new GridLayoutManager(this, 3);
        timeslot_recycle_view.setLayoutManager(timeslotLayoutManager);

        timeslots = new ArrayList<>();
        timeslots.add(new TimeSlots("09:00AM", 3));

        FirebaseDatabase database = FirebaseDatabase.getInstance(Constants.FIREBASE_DATABASE);
        DatabaseReference databaseReference = database.getReference(Constants.TIMESLOT_ENDPOINT);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                timeslots.clear();
                for (DataSnapshot s : snapshot.getChildren()) {
                    TimeSlots f = s.getValue(TimeSlots.class);
                    timeslots.add(f);
                }
                timeslots.sort(new TimeComparator());


                String currentTime = new SimpleDateFormat("hh:mm a").format(new Date());
                Date dateCurrent = Utils.parseDate(currentTime);
                for (TimeSlots t : timeslots) {
                    Date dateActual = Utils.parseDate(t.getTimeslot());
                    if (dateActual.before(dateCurrent))
                        t.setNo_of_appointments(0);
                }
                TimeSlotGridAdaptor timeslotGridAdaptor = new TimeSlotGridAdaptor(TimeSlot_Display_Add.this, timeslots, TimeSlot_Display_Add.this);
                timeslot_recycle_view.setAdapter(timeslotGridAdaptor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}