package com.example.studentverse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button flogin,slogin;

        SharedPreferences sharedPreferences;

        final String KEY_MAIL="mail";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flogin = findViewById(R.id.btnfaculty);
        slogin = findViewById(R.id.btnstudent);

        sharedPreferences=getSharedPreferences("com.example.studentverse_login_status",MODE_PRIVATE);
        boolean status1=sharedPreferences.getBoolean("status1",false);
        Integer status=sharedPreferences.getInt("status",0);
        if (status == 1){
            Intent i=new Intent(MainActivity.this,FacultyDashboard.class);
            startActivity(i);
            finish();
        }
        else if (status == 2){
            Intent i=new Intent(MainActivity.this,StudentDashboard.class);
            startActivity(i);
            finish();
        }
        //flogin btn function
        flogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FacultyLogin.class);
                startActivity(intent);
            }
        });

        //slogin btn function
        slogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,StudentLogin.class);
                startActivity(intent);
            }
        });
    }
}