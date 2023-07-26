package com.example.studentverse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Subjects extends AppCompatActivity {
    EditText subject,staff,syllabus;
    Button update;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference,ref1,ref2,ref3;
    String subjecttxt,stafftxt,syllabustxt,day,cls,sem;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

        subject = findViewById(R.id.subtxt);
        staff = findViewById(R.id.stafftxt);
        syllabus = findViewById(R.id.syllabustxt);
        update = findViewById(R.id.postbtn);

        firebaseDatabase=FirebaseDatabase.getInstance();
        //getting multiple values
        Intent intent = getIntent();
        Bundle extras=intent.getExtras();
        cls= extras.getString("class");
        sem=extras.getString("sem");

        //set database references
        reference=firebaseDatabase.getReference("Register");
        ref1=reference.child(cls).child("Subjects").child(sem);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting subject details
                subjecttxt = subject.getText().toString().trim();
                if (TextUtils.isEmpty(subjecttxt)){
                    subject.setError("This field is required");
                    return;
                }

                //getting staff details
                stafftxt = staff.getText().toString().trim();
                if (TextUtils.isEmpty(stafftxt)){
                    staff.setError("This field is required");
                    return;
                }

                //getting timing details
                syllabustxt = syllabus.getText().toString().trim();
                if (TextUtils.isEmpty(syllabustxt)){
                    syllabus.setError("This field is required");
                    return;
                }


                ref2=ref1.child(subjecttxt+" - "+stafftxt);
                ref2.setValue(syllabustxt);
                subject.setText("");
                staff.setText("");
                syllabus.setText("");
                Toast.makeText(Subjects.this, "Subject Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}