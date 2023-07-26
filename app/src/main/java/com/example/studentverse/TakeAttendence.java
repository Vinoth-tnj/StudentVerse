package com.example.studentverse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TakeAttendence extends AppCompatActivity {
    EditText name;
    Button present,absent;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference,ref1,ref2,ref3,contentref;
    String mailID,cls,nperiod,hour,syllabus,systemdate,replacedmail,nhour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendence);

        name=findViewById(R.id.name_txt);
        present=findViewById(R.id.present_btn);
        absent=findViewById(R.id.absentbtn);

        firebaseDatabase=FirebaseDatabase.getInstance();
        //getting multiple values
        Intent intent = getIntent();
        Bundle extras=intent.getExtras();
        mailID=extras.getString("mail");
        cls= extras.getString("class");
        nperiod=extras.getString("period");
        hour = extras.getString("subject");
        syllabus=extras.getString("content");

        nhour=nperiod;


        //getting system date
        Date date= Calendar.getInstance().getTime();
        SimpleDateFormat df=new SimpleDateFormat("dd-MMM-yyyy");
        systemdate=df.format(date);

        //set database references
        reference=firebaseDatabase.getReference("Register");
        ref1=reference.child(cls).child("Attendence");
        ref2=ref1.child(systemdate).child(nhour);


        //set content reference
        contentref=reference.child(cls).child("Content");
        contentref.child(systemdate).child(nhour).child(hour).setValue(syllabus);

        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting student name
                String studentname=name.getText().toString().trim();
                if (TextUtils.isEmpty(studentname)){
                    name.setError("This field is required");
                    return;
                }
                ref3=ref2.child(studentname);
                ref3.setValue("present");
                name.setText("");
                Toast.makeText(TakeAttendence.this, studentname+" Present", Toast.LENGTH_SHORT).show();
            }
        });

        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting student name
                String studentname=name.getText().toString().trim();
                if (TextUtils.isEmpty(studentname)){
                    name.setError("This field is required");
                    return;
                }
                ref3=ref2.child(studentname);
                ref3.setValue("absent");
                name.setText("");
                Toast.makeText(TakeAttendence.this, studentname+" Absent", Toast.LENGTH_SHORT).show();
            }
        });

    }
}