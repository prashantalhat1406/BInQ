package com.sinprl.binq.pages.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinprl.binq.R;
import com.sinprl.binq.adaptors.AppointmentDetailsAdaptor;
import com.sinprl.binq.adaptors.AppointmentHistoryListAdaptor;
import com.sinprl.binq.dataclasses.Appointment;
import com.sinprl.binq.intefaces.OnItemClickListener;
import com.sinprl.binq.pages.users.User_Appointment_Display;
import com.sinprl.binq.pages.users.User_Appointment_History;
import com.sinprl.binq.utils.Utils;
import com.sinprl.binq.utils.comparators.Appointment_History_Comparator;

import java.util.ArrayList;
import java.util.List;

public class User_Appointment_Details_History extends AppCompatActivity implements OnItemClickListener {

    String userID;
    //FirebaseDatabase database;

    List<Appointment> user_appointments_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_appointment_details_history);

        user_appointments_history = new ArrayList<>();


        userID = getIntent().getExtras().getString("userID","");

        populateUserAppointmentsHistory();



        Toast.makeText(this, "LOK", Toast.LENGTH_SHORT).show();
    }

    public void populateUserAppointmentsHistory() {

        final RecyclerView appointment_recycle_view = findViewById(R.id.list_user_appointments_details_history);
        final LinearLayoutManager appointmentLayoutManager = new LinearLayoutManager(this);
        appointment_recycle_view.setLayoutManager(appointmentLayoutManager);


        //user_appointments_history.add(new Appointment("21", "Pra A", "07:90 pm", "Pain", "1234567895"));
        //DatabaseReference databaseReference = database.getReference("Appointment/");
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://binq-1171a-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference databaseReference = database.getReference("Users/Appointments/"+userID.trim()+"/");//+ Utils.get_current_date_ddmmyy() +"/");

        //Log.d("Appointments", "" + "Users/Appointments/"+userID+"/"+ Utils.get_current_date_ddmmyy() +"/");


        //DatabaseReference databaseReference = database.getReference("Appointment/" + Utils.get_current_date_ddmmyy());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user_appointments_history.clear();

                for (DataSnapshot datewise_appointment_collection : snapshot.getChildren()) {
                    String appointment_date = datewise_appointment_collection.getKey();
                    Log.d("DETAIL", ""+appointment_date);
                    for (DataSnapshot appointment_snapshot : datewise_appointment_collection.getChildren()) {
                        Appointment appointment = appointment_snapshot.getValue(Appointment.class);
                        appointment.setId(appointment_snapshot.getKey());
                        appointment.setDate_of_appointment(appointment_date);
                        user_appointments_history.add(appointment);
                    }
                    user_appointments_history.sort(new Appointment_History_Comparator());
                    AppointmentDetailsAdaptor appointmentListAdaptor = new AppointmentDetailsAdaptor(User_Appointment_Details_History.this, user_appointments_history, User_Appointment_Details_History.this);
                    appointment_recycle_view.setAdapter(appointmentListAdaptor);
                    Toast.makeText(User_Appointment_Details_History.this, "Data arrived", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*databaseReference.addValueEventListener(new ValueEventListener() {
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
                    AppointmentHistoryListAdaptor appointmentListAdaptor = new AppointmentHistoryListAdaptor(User_Appointment_Details_History.this, user_appointments_history, User_Appointment_Details_History.this);
                    appointment_recycle_view.setAdapter(appointmentListAdaptor);
                    Toast.makeText(User_Appointment_Details_History.this, "Data arrived", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(User_Appointment_Details_History.this, "Data error" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });*/
    }

    @Override
    public void onItemClick(View view, int position) {



    }
}