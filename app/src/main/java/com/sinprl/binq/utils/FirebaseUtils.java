package com.sinprl.binq.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinprl.binq.adaptors.AppointmentListAdaptor;
import com.sinprl.binq.dataclasses.Appointment;
import com.sinprl.binq.pages.admin.Admin_Appointment_Display;
import com.sinprl.binq.utils.comparators.AppointmentComparator;

import java.util.ArrayList;
import java.util.List;

public class FirebaseUtils {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance("https://binq-1171a-default-rtdb.asia-southeast1.firebasedatabase.app");
    private static DatabaseReference databaseReference;

    public static List<Appointment> get_current_appointments(String path){
        List<Appointment> appointments = new ArrayList<>();

        if(path.length() == 0)
            databaseReference = database.getReference("Appointment/");
        else
            databaseReference = database.getReference(path);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appointments.clear();
                for (DataSnapshot s : snapshot.getChildren()){
                    Appointment appointment = s.getValue(Appointment.class);
                    appointment.setId(s.getKey());
                    appointments.add(appointment);
                }
                appointments.sort(new AppointmentComparator());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return appointments;
    }
}
