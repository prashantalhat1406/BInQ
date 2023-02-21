package com.sinprl.binq.pages.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinprl.binq.R;
import com.sinprl.binq.adaptors.AppointmentListAdaptor;
import com.sinprl.binq.adaptors.UserListAdaptor;
import com.sinprl.binq.dataclasses.Appointment;
import com.sinprl.binq.dataclasses.User;
import com.sinprl.binq.intefaces.OnItemClickListener;
import com.sinprl.binq.pages.login.Home;
import com.sinprl.binq.utils.Utils;
import com.sinprl.binq.utils.comparators.Appointment_Comparator;
import com.sinprl.binq.utils.comparators.User_Comparator;

import java.util.ArrayList;
import java.util.List;

public class Admin_User_History_Display extends AppCompatActivity implements OnItemClickListener {

    List<User> users;

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_history_display);

        database = FirebaseDatabase.getInstance("https://binq-1171a-default-rtdb.asia-southeast1.firebasedatabase.app");

        populateUsers();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Admin_User_History_Display.this, Admin_Appointment_Display.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    private void populateUsers() {
        final RecyclerView userhistory_recycle_view = findViewById(R.id.list_user_history);
        final LinearLayoutManager userLayoutManager = new LinearLayoutManager(this);
        userhistory_recycle_view.setLayoutManager(userLayoutManager);



        users = new ArrayList<>();
        users.add(new User("Temp","1234567890"));
        DatabaseReference databaseReference = database.getReference("Users/Profiles/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for (DataSnapshot s : snapshot.getChildren()){
                    User user = s.getValue(User.class);
                    users.add(user);
                }

                users.sort(new User_Comparator());
                UserListAdaptor userListAdaptor = new UserListAdaptor(Admin_User_History_Display.this,users, Admin_User_History_Display.this);
                userhistory_recycle_view.setAdapter(userListAdaptor);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        //code to handle appointment display list click

    }
}