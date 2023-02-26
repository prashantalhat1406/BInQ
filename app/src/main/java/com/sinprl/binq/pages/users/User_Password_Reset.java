package com.sinprl.binq.pages.users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sinprl.binq.R;
import com.sinprl.binq.pages.login.Home;
import com.sinprl.binq.utils.Utils;

public class User_Password_Reset extends AppCompatActivity {

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_password_reset);
        userID = getIntent().getExtras().getString("userID","");

        Button reset_password = findViewById(R.id.but_user_reset_password);
        reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset_user_password();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private void reset_user_password() {
        EditText first_password = findViewById(R.id.edt_user_reset_password_first);
        EditText second_password = findViewById(R.id.edt_user_reset_password_second);

        String f_password = first_password.getText().toString().trim();
        String s_password = second_password.getText().toString().trim();

        if(f_password.equals(s_password)){
            if(f_password.length() !=0){
                Utils.update_user_password(f_password, userID);
                Toast.makeText(this, "Password Updated !!", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, "Blank password not allowed", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        }
    }
}