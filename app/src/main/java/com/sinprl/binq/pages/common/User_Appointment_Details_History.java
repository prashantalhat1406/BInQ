package com.sinprl.binq.pages.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinprl.binq.R;
import com.sinprl.binq.adaptors.AppointmentDetailsAdaptor;
import com.sinprl.binq.constants.Constants;
import com.sinprl.binq.dataclasses.Appointment;
import com.sinprl.binq.intefaces.OnItemClickListener;
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
    }

    public void populateUserAppointmentsHistory() {

        final RecyclerView appointment_recycle_view = findViewById(R.id.list_user_appointments_details_history);
        final LinearLayoutManager appointmentLayoutManager = new LinearLayoutManager(this);
        appointment_recycle_view.setLayoutManager(appointmentLayoutManager);

        FirebaseDatabase database = FirebaseDatabase.getInstance(Constants.FIREBASE_DATABASE);
        DatabaseReference databaseReference = database.getReference(Constants.USER_APPOINTMENT_ENDPOINT +userID.trim()+"/");//+ Utils.get_current_date_ddmmyy() +"/");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
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
                    AppointmentDetailsAdaptor appointmentListAdaptor = new AppointmentDetailsAdaptor(User_Appointment_Details_History.this, user_appointments_history, User_Appointment_Details_History.this);
                    appointment_recycle_view.setAdapter(appointmentListAdaptor);
                }
                Appointment a = user_appointments_history.get(0);
                TextView name = findViewById(R.id.txt_user_appointment_details_username);
                name.setText(a.getUser_name());
                TextView phone = findViewById(R.id.txt_user_appointment_details_phone);
                phone.setText(" " + a.getPhone());
                TextView age = findViewById(R.id.txt_user_appointment_details_age);
                age.setText(""+a.getAge());
                TextView gender = findViewById(R.id.txt_user_appointment_details_gender);

                if(a.getGender() == 1){
                    gender.setText("Male");
                    gender.setCompoundDrawablesWithIntrinsicBounds(R.drawable.male, 0, 0, 0);

                } else if (a.getGender() ==2) {
                    gender.setText("Female");
                    gender.setCompoundDrawablesWithIntrinsicBounds(R.drawable.female, 0, 0, 0);
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