package com.sinprl.binq.pages.users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinprl.binq.R;
import com.sinprl.binq.adaptors.AppointmentListAdaptor;
import com.sinprl.binq.dataclasses.Appointment;
import com.sinprl.binq.dataclasses.User;
import com.sinprl.binq.intefaces.OnItemClickListener;
import com.sinprl.binq.pages.login.Home;
import com.sinprl.binq.utils.Utils;
import com.sinprl.binq.utils.comparators.Appointment_Comparator;

import java.util.ArrayList;
import java.util.List;

public class User_Appointment_Display extends AppCompatActivity implements OnItemClickListener {


    FirebaseDatabase database;
    String userID;
    TextView status_text;
    List<Appointment> userappointments;
    List<Appointment> all_day_appointments;

    User current_user;
    Button status;
    RecyclerView appointment_recycle_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_appointment_display);

        database = FirebaseDatabase.getInstance("https://binq-1171a-default-rtdb.asia-southeast1.firebasedatabase.app");

        userID = getIntent().getExtras().getString("userID","");

        appointment_recycle_view = findViewById(R.id.list_user_appointments);

        fetch_current_user_details(userID);
        populateAppointments();
        populate_allDay_Appointments();

        FloatingActionButton addAppointment = findViewById(R.id.fab_user_add_appointment);
        addAppointment.setOnClickListener(view -> {

            Intent intent = new Intent(User_Appointment_Display.this, User_Appointment_Add.class);
            intent.putExtra("userID", userID);
            intent.putExtra("userName", current_user.getName());
            intent.putExtra("userPhone", current_user.getPhone());
            startActivity(intent);
        });

        status_text = findViewById(R.id.txt_appointment_display_status);

        status = findViewById(R.id.button_status_of_appointment);
        status.setOnClickListener(view -> calalculate_current_status());



    }

    private void fetch_current_user_details(String userID) {
        DatabaseReference databaseReference = database.getReference("Users/Profiles/"+userID);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                current_user = snapshot.getValue(User.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void calalculate_current_status() {
        int counter = 0;
        boolean active_appointment = false;
        for (Appointment appointment: all_day_appointments) {
            if(! appointment.getUserID().equals(userID) ) {
                if (appointment.getActive() == 1)
                    counter++;
            }
            else {
                if(appointment.getActive() == 1){
                    active_appointment = true;
                    break;
                }
            }
        }

        if(active_appointment)
            status_text.setText("Your are " + (counter + 1) + " in Q");
        else
            status_text.setText(getResources().getString(R.string.no_active_appointment));
    }

    private void populate_allDay_Appointments() {
        DatabaseReference databaseReference = database.getReference("Appointment/" + Utils.get_current_date_ddmmyy());

        all_day_appointments = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                all_day_appointments.clear();
                for (DataSnapshot s : snapshot.getChildren()){
                    Appointment appointment = s.getValue(Appointment.class);
                    appointment.setId(s.getKey());
                    all_day_appointments.add(appointment);
                }
                all_day_appointments.sort(new Appointment_Comparator());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(User_Appointment_Display.this, Home.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(this, "Activity Resumed", Toast.LENGTH_SHORT).show();
        calalculate_current_status();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.user_appointment_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.menu_user_logout){
            Intent intent = new Intent(User_Appointment_Display.this, Home.class);
            startActivity(intent);
            finish();
        }
        if(item.getItemId() == R.id.menu_user_show_history){
            Intent intent = new Intent(User_Appointment_Display.this, User_Appointment_History.class);
            intent.putExtra("userID", userID);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    private void populateAppointments() {

        final LinearLayoutManager appointmentLayoutManager = new LinearLayoutManager(this);
        appointment_recycle_view.setLayoutManager(appointmentLayoutManager);

        userappointments = new ArrayList<>();
        userappointments.add(new Appointment("21", "Pra A", "07:90 pm", "Pain", "1234567895"));
        DatabaseReference databaseReference = database.getReference("Users/Appointments/"+userID+"/" + Utils.get_current_date_ddmmyy());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userappointments.clear();
                for (DataSnapshot s : snapshot.getChildren()){
                    Log.d("Regular", "" + s.getValue());
                    Appointment appointment = s.getValue(Appointment.class);
                    appointment.setId(s.getKey());
                    userappointments.add(appointment);
                }
                userappointments.sort(new Appointment_Comparator());
                boolean enable_status_button = false;
                for (Appointment a: userappointments) {
                    if(a.getActive() == 1)
                    {
                        enable_status_button = true;
                        break;
                    }
                }
                if(!enable_status_button)
                    status_text.setText(getResources().getString(R.string.no_active_appointment));


                AppointmentListAdaptor appointmentListAdaptor = new AppointmentListAdaptor(User_Appointment_Display.this,userappointments, User_Appointment_Display.this,false);
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

        if(userappointments.get(position).getActive() == 1)
        {
            final Dialog dialog = new Dialog(User_Appointment_Display.this);
            dialog.setContentView(R.layout.dialog_appointment_action);
            dialog.setCancelable(true);

            dialog.getWindow().setLayout(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);


            Button cancel = dialog.findViewById(R.id.appointment_cancel);
            cancel.setOnClickListener(v -> {
                Utils.cancel_appointment(userappointments.get(position).getId(), userappointments.get(position).getUserID());
                dialog.dismiss();
            });

            Button done = dialog.findViewById(R.id.appointment_done);
            done.setVisibility(View.GONE);
            dialog.show();
        }
    }

}