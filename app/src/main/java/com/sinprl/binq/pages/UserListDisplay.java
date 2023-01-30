package com.sinprl.binq.pages;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinprl.binq.adaptors.FruitListAdaptor;
import com.sinprl.binq.databinding.ActivityUserListDisplayBinding;
//import com.sinprl.binq.pages.databinding.ActivityUserListDisplayBinding;

import com.sinprl.binq.R;
import com.sinprl.binq.dataclasses.Fruits;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class UserListDisplay extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityUserListDisplayBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_display);

        final RecyclerView recyclerFruit = (RecyclerView) findViewById(R.id.list_fruits);
        final LinearLayoutManager fruitsLayoutManager = new LinearLayoutManager(this);
        recyclerFruit.setLayoutManager(fruitsLayoutManager);

        List<Fruits> allFruits = new ArrayList<Fruits>();
        allFruits.add(new Fruits("Kiwi", "Rajstan"));
        /*allFruits.add(new Fruits("Peru", "Kerala"));
        allFruits.add(new Fruits("Kaju", "Goa"));*/



        FirebaseDatabase database = FirebaseDatabase.getInstance("https://binq-1171a-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference databaseReference = database.getReference("Fruits");

        //databaseReference.child("Fruits23").setValue(new Fruits("Wangi", "Mizoram"));
        /*databaseReference.child("Fruits5").setValue(new Fruits("Chikoo","Tamilnadu"));
        databaseReference.child("Fruits6").setValue(new Fruits("Lemon","Bangal"));
        databaseReference.child("Fruits7").setValue(new Fruits("Gwar","Gujraat"));*/

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()){
                    Fruits f = s.getValue(Fruits.class);
                    allFruits.add(f);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FruitListAdaptor fla = new FruitListAdaptor(this,allFruits);
        recyclerFruit.setAdapter(fla);

        //https://binq-1171a-default-rtdb.asia-southeast1.firebasedatabase.app/Fruits.json




        // binding = ActivityUserListDisplayBinding.inflate(getLayoutInflater());
        // setContentView(binding.getRoot());


        // setSupportActionBar(binding.toolbar);

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_user_list_display);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        /*binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_user_list_display);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}