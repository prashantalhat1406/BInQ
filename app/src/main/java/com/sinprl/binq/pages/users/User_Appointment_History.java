package com.sinprl.binq.pages.users;

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
import com.sinprl.binq.dataclasses.Appointment;
import com.sinprl.binq.intefaces.OnItemClickListener;
import com.sinprl.binq.pages.admin.Admin_Appointment_History;
import com.sinprl.binq.utils.Utils;
import com.sinprl.binq.utils.comparators.Appointment_History_Comparator;

import java.util.ArrayList;
import java.util.List;

public class User_Appointment_History extends AppCompatActivity implements OnItemClickListener {

    FirebaseDatabase database;
    String userID;

    List<Appointment> user_appointments_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_appointment_history);

        userID = getIntent().getExtras().getString("userID","");

        database = FirebaseDatabase.getInstance("https://binq-1171a-default-rtdb.asia-southeast1.firebasedatabase.app");

        populateUserAppointmentsHistory();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private void populateUserAppointmentsHistory() {

        final RecyclerView appointment_recycle_view = findViewById(R.id.list_user_appointments_history);
        final LinearLayoutManager appointmentLayoutManager = new LinearLayoutManager(this);
        appointment_recycle_view.setLayoutManager(appointmentLayoutManager);

        user_appointments_history = new ArrayList<>();
        user_appointments_history.add(new Appointment("21", "Pra A", "07:90 pm", "Pain", "1234567895"));
        //DatabaseReference databaseReference = database.getReference("Appointment/");
        DatabaseReference databaseReference = database.getReference("Users/Appointments/"+userID+"/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user_appointments_history.clear();
                for (DataSnapshot datewise_appointment_collection : snapshot.getChildren()) {
                    String appointment_date = datewise_appointment_collection.getKey();
                    for (DataSnapshot appointment_snapshot : datewise_appointment_collection.getChildren()) {
                        Appointment appointment = appointment_snapshot.getValue(Appointment.class);
                        appointment.setId(appointment_snapshot.getKey());
                        appointment.setDate_of_appointment(appointment_date);
                        user_appointments_history.add(appointment);
                    }

                    user_appointments_history.sort(new Appointment_History_Comparator());
                    AppointmentHistoryListAdaptor appointmentListAdaptor = new AppointmentHistoryListAdaptor(User_Appointment_History.this, user_appointments_history, User_Appointment_History.this);
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