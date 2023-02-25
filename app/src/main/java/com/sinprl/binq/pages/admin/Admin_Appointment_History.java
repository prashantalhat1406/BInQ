package com.sinprl.binq.pages.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinprl.binq.R;
import com.sinprl.binq.adaptors.AppointmentHistoryListAdaptor;
import com.sinprl.binq.adaptors.AppointmentListAdaptor;
import com.sinprl.binq.constants.Constants;
import com.sinprl.binq.dataclasses.Appointment;
import com.sinprl.binq.intefaces.OnItemClickListener;
import com.sinprl.binq.utils.Utils;
import com.sinprl.binq.utils.comparators.Appointment_Comparator;
import com.sinprl.binq.utils.comparators.Appointment_History_Comparator;

import java.util.ArrayList;
import java.util.List;

public class Admin_Appointment_History extends AppCompatActivity implements OnItemClickListener {

    FirebaseDatabase database;

    List<Appointment> appointments_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_appointment_history);

        database = FirebaseDatabase.getInstance(Constants.FIREBASE_DATABASE);

        populateAppointmentsHistory();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private void populateAppointmentsHistory() {

        final RecyclerView appointment_recycle_view = findViewById(R.id.list_appointments_history);
        final LinearLayoutManager appointmentLayoutManager = new LinearLayoutManager(this);
        appointment_recycle_view.setLayoutManager(appointmentLayoutManager);

        appointments_history = new ArrayList<>();
        appointments_history.add(new Appointment("21", "Pra A", "07:90 pm", "Pain", "1234567895"));
        DatabaseReference databaseReference = database.getReference(Constants.APPOINTMENT_ENDPOINT);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appointments_history.clear();
                for (DataSnapshot datewise_appointment_collection : snapshot.getChildren()) {
                    String appointment_date = datewise_appointment_collection.getKey();
                    for (DataSnapshot appointment_snapshot : datewise_appointment_collection.getChildren()) {
                        Appointment appointment = appointment_snapshot.getValue(Appointment.class);
                        appointment.setId(appointment_snapshot.getKey());
                        appointment.setDate_of_appointment(appointment_date);
                        appointments_history.add(appointment);
                    }

                    appointments_history.sort(new Appointment_History_Comparator());
                    AppointmentHistoryListAdaptor appointmentListAdaptor = new AppointmentHistoryListAdaptor(Admin_Appointment_History.this, appointments_history, Admin_Appointment_History.this);
                    appointment_recycle_view.setAdapter(appointmentListAdaptor);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}