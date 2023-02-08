package com.sinprl.binq.pages.appointment_admin;

import android.content.Intent;
import android.os.Bundle;



import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import com.sinprl.binq.adaptors.AppointmentListAdaptor;
import com.sinprl.binq.R;
import com.sinprl.binq.dataclasses.Appointment;
import com.sinprl.binq.intefaces.OnItemClickListener;
import com.sinprl.binq.utils.Utils;


public class Admin_Appointment_Display extends AppCompatActivity implements OnItemClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_display);

        Log.d("DATE", "" + Utils.get_current_date_ddmmyy());

        populateAppointments();
        //showreason();

        FloatingActionButton addAppointment = findViewById(R.id.fab_add_appointment);
        addAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_Appointment_Display.this, Admin_Appointment_Add.class);
                startActivity(intent);
            }
        });
    }




    private void populateAppointments() {

        final RecyclerView appointment_recycle_view = findViewById(R.id.list_appointments);
        final LinearLayoutManager appointmentLayoutManager = new LinearLayoutManager(this);
        appointment_recycle_view.setLayoutManager(appointmentLayoutManager);

        List<Appointment> appointments = new ArrayList<>();
        appointments.add(new Appointment("21", "Pra A", "07:90 pm", "Pain", "1234567895"));
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://binq-1171a-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference databaseReference = database.getReference("Appointment/" + Utils.get_current_date_ddmmyy());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appointments.clear();
                for (DataSnapshot s : snapshot.getChildren()){
                    Log.d("Test", s.toString());
                    Appointment f = s.getValue(Appointment.class);
                    appointments.add(f);
                }
                AppointmentListAdaptor appointmentListAdaptor = new AppointmentListAdaptor(Admin_Appointment_Display.this,appointments, Admin_Appointment_Display.this);
                appointment_recycle_view.setAdapter(appointmentListAdaptor);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        //code to handle appointment display list click
        Toast.makeText(view.getContext(), position + "", Toast.LENGTH_SHORT).show();
        //view.setBackground(ContextCompat.getDrawable(Appointment_Display.this, R.drawable.green_border_rectangle));
    }
}