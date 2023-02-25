package com.sinprl.binq.pages.admin;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;



import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;


import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import com.sinprl.binq.constants.Constants;
import com.sinprl.binq.dataclasses.Appointment;
import com.sinprl.binq.dataclasses.TimeSlots;
import com.sinprl.binq.intefaces.OnItemClickListener;
import com.sinprl.binq.pages.login.Home;
import com.sinprl.binq.pages.users.User_Appointment_Display;
import com.sinprl.binq.utils.comparators.Appointment_Comparator;
import com.sinprl.binq.utils.Utils;


public class Admin_Appointment_Display extends AppCompatActivity implements OnItemClickListener, RadioGroup.OnCheckedChangeListener {

    List<Appointment> appointments, master_appointments;
    List<TimeSlots> timeslots;
    FirebaseDatabase database;
    RadioGroup appointment_filter_group;
    RadioButton appointment_all,appointment_active,appointment_done,appointment_cancel;
    RecyclerView appointment_recycle_view;
    AppointmentListAdaptor appointmentListAdaptor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_appointment_display);
        //database = FirebaseDatabase.getInstance("https://binq-1171a-default-rtdb.asia-southeast1.firebasedatabase.app");
        database = FirebaseDatabase.getInstance(Constants.FIREBASE_DATABASE);

        appointment_recycle_view = findViewById(R.id.list_appointments);
        LinearLayoutManager appointmentLayoutManager = new LinearLayoutManager(this);
        appointment_recycle_view.setLayoutManager(appointmentLayoutManager);
        appointments = new ArrayList<>();
        master_appointments = new ArrayList<>();
        appointmentListAdaptor = new AppointmentListAdaptor(Admin_Appointment_Display.this,appointments, Admin_Appointment_Display.this,true);
        appointment_recycle_view.setAdapter(appointmentListAdaptor);

        appointment_filter_group = findViewById(R.id.rdgroup_appointment_filter);
        appointment_filter_group.setOnCheckedChangeListener(this);
        appointment_all = findViewById(R.id.rdbutton_appointment_filter_all);
        appointment_active = findViewById(R.id.rdbutton_appointment_filter_active);
        appointment_done = findViewById(R.id.rdbutton_appointment_filter_done);
        appointment_cancel = findViewById(R.id.rdbutton_appointment_filter_cancel);

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




        appointments.add(new Appointment("21", "Pra A", "07:90 pm", "Pain", "1234567895"));
        master_appointments.add(new Appointment("21", "Pra A", "07:90 pm", "Pain", "1234567895"));
        DatabaseReference databaseReference = database.getReference(Constants.APPOINTMENT_ENDPOINT + Utils.get_current_date_ddmmyy());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appointments.clear();
                master_appointments.clear();
                for (DataSnapshot s : snapshot.getChildren()){
                    Appointment appointment = s.getValue(Appointment.class);
                    appointment.setId(s.getKey());
                    appointments.add(appointment);
                }

                appointments.sort(new Appointment_Comparator());
                master_appointments.addAll(appointments);
                appointmentListAdaptor.notifyDataSetChanged();


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Admin_Appointment_Display.this, Home.class);
        startActivity(intent);
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
            Intent intent = new Intent(Admin_Appointment_Display.this, Home.class);
            startActivity(intent);
            finish();
        }
        if(item.getItemId()==R.id.menu_admin_user_history){
            Intent intent = new Intent(Admin_Appointment_Display.this, Admin_User_History_Display.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void reset_appointment_slots() {

        for (TimeSlots timeslot: timeslots) {
            DatabaseReference timeslot_ref = database.getReference(Constants.TIMESLOT_ENDPOINT + timeslot.getId() + "/" );
            timeslot_ref.child("no_of_appointments/").setValue(3);
        }
    }

    private void fetch_timeslots_from_database() {

        timeslots = new ArrayList<>();
        //timeslots.add(new TimeSlots("09:00AM", 3));

        DatabaseReference databaseReference = database.getReference(Constants.TIMESLOT_ENDPOINT);
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

            String userID = ((TextView) view.findViewById(R.id.txt_apt_item_phone)).getText().toString().trim();

            Intent intent = new Intent(view.getContext(), Admin_Appointment_Action.class);
            intent.putExtra("userID", userID);
            intent.putExtra("appointmentID", appointments.get(position).getId());
            intent.putExtra("username", appointments.get(position).getUser_name());
            intent.putExtra("userage", appointments.get(position).getAge());
            intent.putExtra("usergender", appointments.get(position).getGender());
            intent.putExtra("userphone", appointments.get(position).getPhone());
            intent.putExtra("reason", appointments.get(position).getReason());
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        appointments.clear();
        switch (i){
            case R.id.rdbutton_appointment_filter_all:
                appointments.addAll(master_appointments);
                break;
            case R.id.rdbutton_appointment_filter_active:
                for (Appointment appointment: master_appointments) {
                    if(appointment.getActive() == 1)
                        appointments.add(appointment);
                }
                break;
            case R.id.rdbutton_appointment_filter_done:
                for (Appointment appointment: master_appointments) {
                    if(appointment.getActive() == 0)
                        appointments.add(appointment);
                }
                break;
            case R.id.rdbutton_appointment_filter_cancel:
                for (Appointment appointment: master_appointments) {
                    if(appointment.getActive() == 2)
                        appointments.add(appointment);
                }
                break;
        }
        appointmentListAdaptor.notifyDataSetChanged();
    }
}