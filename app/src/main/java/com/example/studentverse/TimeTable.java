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

public class TimeTable extends AppCompatActivity {
    EditText subject,staff,time;
    Button update;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference,ref1,ref2,ref3;
    String subjecttxt,stafftxt,timetxt,day,cls,sem;
    Spinner spinner;
    String[] totalday={"","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        spinner = findViewById(R.id.spinnerday);
        subject = findViewById(R.id.subtxt);
        staff = findViewById(R.id.stafftxt);
        time = findViewById(R.id.timetxt);
        update = findViewById(R.id.postbtn);

        firebaseDatabase=FirebaseDatabase.getInstance();
        //getting multiple values
        Intent intent = getIntent();
        Bundle extras=intent.getExtras();
        cls= extras.getString("class");
        sem=extras.getString("sem");

        //spinner
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,totalday);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        //set database references
        reference=firebaseDatabase.getReference("Register");
        ref1=reference.child(cls).child("TimeTable").child(sem);

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
                timetxt = time.getText().toString().trim();
                if (TextUtils.isEmpty(timetxt)){
                    time.setError("This field is required");
                    return;
                }

                //getting day from spinner(dropdown)
                day = spinner.getSelectedItem().toString();
                if(TextUtils.isEmpty(day)){
                    ((TextView)spinner.getSelectedView()).setError("Error message");
                    return;
                }


                ref2=ref1.child(day).child(timetxt);
                ref3=ref2.child(subjecttxt);
                ref3.setValue(stafftxt);
                subject.setText("");
                staff.setText("");
                time.setText("");
                Toast.makeText(TimeTable.this, "Timetable Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}