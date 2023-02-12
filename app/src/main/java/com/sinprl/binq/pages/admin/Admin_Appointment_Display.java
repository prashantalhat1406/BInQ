package com.sinprl.binq.pages.admin;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;



import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


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
import com.sinprl.binq.dataclasses.TimeSlots;
import com.sinprl.binq.intefaces.OnItemClickListener;
import com.sinprl.binq.utils.comparators.Appointment_Comparator;
import com.sinprl.binq.utils.Utils;


public class Admin_Appointment_Display extends AppCompatActivity implements OnItemClickListener {

    List<Appointment> appointments;
    List<TimeSlots> timeslots;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_appointment_display);
        database = FirebaseDatabase.getInstance("https://binq-1171a-default-rtdb.asia-southeast1.firebasedatabase.app");

        populateAppointments();
        fetch_timeslots_from_database();
        //showreason();

        FloatingActionButton addAppointment = findViewById(R.id.fab_add_appointment);
        addAppointment.setOnClickListener(view -> {
            Intent intent = new Intent(Admin_Appointment_Display.this, Admin_Appointment_Add.class);
            startActivity(intent);
        });
    }




    private void populateAppointments() {

        final RecyclerView appointment_recycle_view = findViewById(R.id.list_appointments);
        final LinearLayoutManager appointmentLayoutManager = new LinearLayoutManager(this);
        appointment_recycle_view.setLayoutManager(appointmentLayoutManager);

        appointments = new ArrayList<>();
        appointments.add(new Appointment("21", "Pra A", "07:90 pm", "Pain", "1234567895"));
        DatabaseReference databaseReference = database.getReference("Appointment/" + Utils.get_current_date_ddmmyy());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appointments.clear();
                for (DataSnapshot s : snapshot.getChildren()){
                    Appointment appointment = s.getValue(Appointment.class);
                    appointment.setId(s.getKey());
                    appointments.add(appointment);
                }

                appointments.sort(new Appointment_Comparator());
                AppointmentListAdaptor appointmentListAdaptor = new AppointmentListAdaptor(Admin_Appointment_Display.this,appointments, Admin_Appointment_Display.this);
                appointment_recycle_view.setAdapter(appointmentListAdaptor);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.admin_appointment_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.menu_admin_reset_token){
            database.getReference("TokenNumber").setValue(1);
        }
        if (item.getItemId()==R.id.menu_admin_reset_timeslot){
            reset_appointment_slots();
        }
        if (item.getItemId()==R.id.menu_admin_logout){
            finish();
        }
        if(item.getItemId()==R.id.menu_admin_history){
            Intent intent = new Intent(Admin_Appointment_Display.this, Admin_Appointment_History.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void reset_appointment_slots() {

        for (TimeSlots timeslot: timeslots) {
            DatabaseReference timeslot_ref = database.getReference("Timeslots/" + timeslot.getId() + "/" );
            timeslot_ref.child("no_of_appointments/").setValue(3);
        }
    }

    private void fetch_timeslots_from_database() {

        timeslots = new ArrayList<>();
        //timeslots.add(new TimeSlots("09:00AM", 3));

        DatabaseReference databaseReference = database.getReference("Timeslots/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                timeslots.clear();
                for (DataSnapshot s : snapshot.getChildren()) {
                    TimeSlots timeslot = s.getValue(TimeSlots.class);
                    timeslot.setId(s.getKey());
                    timeslots.add(timeslot);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        //code to handle appointment display list click
        if(appointments.get(position).getActive() == 1) {

            final Dialog dialog = new Dialog(Admin_Appointment_Display.this);
            dialog.setContentView(R.layout.dialog_appointment_action);
            dialog.setCancelable(true);

            dialog.getWindow().setLayout(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);


            Button cancel = dialog.findViewById(R.id.appointment_cancel);
            cancel.setOnClickListener(v -> {
                Utils.cancel_appointment(appointments.get(position).getId(), appointments.get(position).getUserID());
                dialog.dismiss();
            });

            Button done = dialog.findViewById(R.id.appointment_done);
            done.setOnClickListener(v -> {
                Utils.mark_appointment_done(appointments.get(position).getId(), appointments.get(position).getUserID());
                dialog.dismiss();
            });
            dialog.show();
        }
    }
}