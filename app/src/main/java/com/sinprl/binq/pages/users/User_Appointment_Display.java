package com.sinprl.binq.pages.users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinprl.binq.R;
import com.sinprl.binq.adaptors.AppointmentListAdaptor;
import com.sinprl.binq.dataclasses.Appointment;
import com.sinprl.binq.intefaces.OnItemClickListener;
import com.sinprl.binq.pages.admin.Admin_Appointment_Display;
import com.sinprl.binq.utils.FirebaseUtils;
import com.sinprl.binq.utils.Utils;
import com.sinprl.binq.utils.comparators.AppointmentComparator;

import java.util.ArrayList;
import java.util.List;

public class User_Appointment_Display extends AppCompatActivity implements OnItemClickListener {


    FirebaseDatabase database;
    String userID;

    List<Appointment> userappointments;
    List<Appointment> all_day_appointments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_appointment_display);

        database = FirebaseDatabase.getInstance("https://binq-1171a-default-rtdb.asia-southeast1.firebasedatabase.app");

        userID = getIntent().getExtras().getString("userID","");

        populateAppointments();
        populate_allDay_Appointments();

        FloatingActionButton addAppointment = findViewById(R.id.fab_user_add_appointment);
        addAppointment.setOnClickListener(view -> {
            Intent intent = new Intent(User_Appointment_Display.this, User_Appointment_Add.class);
            intent.putExtra("userID", userID);
            startActivity(intent);
        });

        Button status = findViewById(R.id.button_status_of_appointment);
        status.setOnClickListener(view -> calalculate_current_status());


    }

    private void calalculate_current_status() {
        int counter = 0;
        for (Appointment appointment: all_day_appointments) {
            if(! appointment.getUserID().equals(userID) ) {
                if (appointment.getActive() == 1)
                    counter++;
            }
            else
                break;
        }

        TextView status_text = findViewById(R.id.txt_appointment_display_status);
        status_text.setText("Your Number is " + (counter + 1));

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
                all_day_appointments.sort(new AppointmentComparator());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void populateAppointments() {
        final RecyclerView appointment_recycle_view = findViewById(R.id.list_user_appointments);
        final LinearLayoutManager appointmentLayoutManager = new LinearLayoutManager(this);
        appointment_recycle_view.setLayoutManager(appointmentLayoutManager);

        userappointments = new ArrayList<>();
        userappointments.add(new Appointment("21", "Pra A", "07:90 pm", "Pain", "1234567895"));
        DatabaseReference databaseReference = database.getReference("Users/"+userID+"/Appointments/" + Utils.get_current_date_ddmmyy());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userappointments.clear();
                for (DataSnapshot s : snapshot.getChildren()){
                    Log.d("Test", s.toString());
                    Appointment f = s.getValue(Appointment.class);
                    f.setId(s.getKey());
                    userappointments.add(f);
                }
                AppointmentListAdaptor appointmentListAdaptor = new AppointmentListAdaptor(User_Appointment_Display.this,userappointments, User_Appointment_Display.this);
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
            done.setOnClickListener(v -> {
                Utils.mark_appointment_done(userappointments.get(position).getId(), userappointments.get(position).getUserID());
                dialog.dismiss();
            });
            dialog.show();
        }
    }

}