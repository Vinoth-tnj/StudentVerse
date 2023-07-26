package com.example.studentverse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Internals extends AppCompatActivity {
    EditText sub1,sub2,sub3,sub4,sub5,sub6,sub7,mark1,mark2,mark3,mark4,mark5,mark6,mark7;
    Button update;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference,ref1,ref2,ref3,ref4,ref5,ref6,ref7,ref8,ref9,ref10;
    String cls,sub1txt,sub2txt,sub3txt,sub4txt,sub5txt,sub6txt,sub7txt,mark1txt,mark2txt,mark3txt,mark4txt,mark5txt,mark6txt,mark7txt,student,semester,replacedmail,newstu;
    Spinner spinnerstu,spinnersem;
    String[] totalsem={"","I","II","III","IV","V","VI"};
    String[] totalstudent={"","I","II","III","IV","V","VI"};
    ArrayList<String> arrayList1=new ArrayList<>();
    ArrayAdapter arrayAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internals);

        spinnerstu = findViewById(R.id.spinnerstu);
        spinnersem = findViewById(R.id.spinnersem);
        sub1 = findViewById(R.id.subject1_inter_txt);
        sub2 = findViewById(R.id.subject2_inter_txt);
        sub3 = findViewById(R.id.subject3_inter_txt);
        sub4 = findViewById(R.id.subject4_inter_txt);
        sub5 = findViewById(R.id.subject5_inter_txt);
        sub6 = findViewById(R.id.subject6_inter_txt);
        sub7 = findViewById(R.id.subject7_inter_txt);
        mark1 = findViewById(R.id.mark1_inter_txt);
        mark2 = findViewById(R.id.mark2_inter_txt);
        mark3 = findViewById(R.id.mark3_inter_txt);
        mark4 = findViewById(R.id.mark4_inter_txt);
        mark5 = findViewById(R.id.mark5_inter_txt);
        mark6 = findViewById(R.id.mark6_inter_txt);
        mark7 = findViewById(R.id.mark7_inter_txt);
        update = findViewById(R.id.updatebtn);

        firebaseDatabase=FirebaseDatabase.getInstance();
        //getting multiple values
        Intent intent = getIntent();
        Bundle extras=intent.getExtras();
        cls= extras.getString("class");

        arrayAdapter1 =new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList1);
        arrayList1.clear();
        arrayAdapter1.notifyDataSetChanged();

        //getting student mailid from firebase
        reference= FirebaseDatabase.getInstance().getReference().child("studentusers");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String mail=snapshot.getKey().toString();
                //replaced mail
                replacedmail=mail.replace(",",".");
                arrayList1.add(replacedmail);
                arrayAdapter1.notifyDataSetChanged();
                spinnerstu.setAdapter(arrayAdapter1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //spinner(dropdown) for sem
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,totalsem);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnersem.setAdapter(adapter);

        //spinner(dropdown) for sem
        ArrayAdapter<String> adapter1=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,arrayList1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerstu.setAdapter(adapter1);

        //set database references
        reference=firebaseDatabase.getReference("Register");
        ref1=reference.child(cls).child("Internals");

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting subject details
                sub1txt = sub1.getText().toString().trim();
                sub2txt = sub2.getText().toString().trim();
                sub3txt = sub3.getText().toString().trim();
                sub4txt = sub4.getText().toString().trim();
                sub5txt = sub5.getText().toString().trim();
                sub6txt = sub6.getText().toString().trim();
                sub7txt = sub7.getText().toString().trim();
                if (TextUtils.isEmpty(sub1txt)){
                    sub1.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(sub2txt)){
                    sub2.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(sub3txt)){
                    sub3.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(sub4txt)){
                    sub4.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(sub5txt)){
                    sub5.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(sub6txt)){
                    sub6.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(sub7txt)){
                    sub7.setError("This field is required");
                    return;
                }

                //getting mark details
                mark1txt = mark1.getText().toString().trim();
                mark2txt = mark2.getText().toString().trim();
                mark3txt = mark3.getText().toString().trim();
                mark4txt = mark4.getText().toString().trim();
                mark5txt = mark5.getText().toString().trim();
                mark6txt = mark6.getText().toString().trim();
                mark7txt = mark7.getText().toString().trim();
                if (TextUtils.isEmpty(mark1txt)){
                    mark1.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(mark2txt)){
                    mark2.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(mark3txt)){
                    mark3.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(mark4txt)){
                    mark4.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(mark5txt)){
                    mark5.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(mark6txt)){
                    mark6.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(mark7txt)){
                    mark7.setError("This field is required");
                    return;
                }

                //getting student from spinner(dropdown)
                student = spinnerstu.getSelectedItem().toString();
                if(TextUtils.isEmpty(student)){
                    ((TextView)spinnerstu.getSelectedView()).setError("Error message");
                    return;
                }

                //getting semester from spinner(dropdown)
                semester = spinnersem.getSelectedItem().toString();
                if(TextUtils.isEmpty(semester)){
                    ((TextView)spinnersem.getSelectedView()).setError("Error message");
                    return;
                }

                //dot(.) didn't store in fire so . replaced with ,
                newstu = student.replace(".",",");


                ref2=ref1.child(semester).child(newstu);
                ref3=ref2.child(sub1txt);
                ref3.setValue(mark1txt);
                ref4=ref2.child(sub2txt);
                ref4.setValue(mark2txt);
                ref5=ref2.child(sub3txt);
                ref5.setValue(mark3txt);
                ref6=ref2.child(sub4txt);
                ref6.setValue(mark4txt);
                ref7=ref2.child(sub5txt);
                ref7.setValue(mark5txt);
                ref8=ref2.child(sub6txt);
                ref8.setValue(mark6txt);
                ref9=ref2.child(sub7txt);
                ref9.setValue(mark7txt);
                sub1.setText("");
                sub2.setText("");
                sub3.setText("");
                sub4.setText("");
                sub5.setText("");
                sub6.setText("");
                sub7.setText("");
                mark1.setText("");
                mark2.setText("");
                mark3.setText("");
                mark4.setText("");
                mark5.setText("");
                mark6.setText("");
                mark7.setText("");
                Toast.makeText(Internals.this, "Mark Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}