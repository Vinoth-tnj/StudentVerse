package com.example.studentverse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewAttendence extends AppCompatActivity {
    DatabaseReference reference;
    ListView present,absent;
    Spinner spinner;
    TextView pre_txt,abs_txt;
    Button fetch;
    ArrayList<String> arrayList1=new ArrayList<>();
    ArrayList<String> arrayList2=new ArrayList<>();
    ArrayAdapter arrayAdapter1,arrayAdapter2;
    String mailID,ndate,cls,replacedmail;
    String hour;
    String[] totalhour={"1","2","3","4","5","6","7"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendence);

        //getting multiple values
        Intent intent = getIntent();
        Bundle extras=intent.getExtras();
        mailID=extras.getString("mail");
        ndate=extras.getString("date");
        cls=extras.getString("cls");

        //replaced mail
        replacedmail=mailID.replace(".",",");

        //spinner
        spinner =findViewById(R.id.spinner);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,totalhour);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        present=findViewById(R.id.present_list);
        absent=findViewById(R.id.absent_list);
        fetch=findViewById(R.id.fetchbtn);
        pre_txt=findViewById(R.id.present_textview);
        abs_txt=findViewById(R.id.absent_textview);

        arrayAdapter1 =new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList1);
        arrayAdapter2 =new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList2);

        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList1.clear();
                arrayAdapter1.notifyDataSetChanged();
                arrayList2.clear();
                arrayAdapter2.notifyDataSetChanged();

                //view head
                pre_txt.setVisibility(View.VISIBLE);
                abs_txt.setVisibility(View.VISIBLE);

                hour=spinner.getSelectedItem().toString();

                reference= FirebaseDatabase.getInstance().getReference().child("Register").child(cls).child("Attendence").child(ndate).child(hour);
                reference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String name=snapshot.getKey();
                        String status=snapshot.getValue().toString();

                        if (status.equalsIgnoreCase("Present")){
                            arrayList1.add(name);
                            arrayAdapter1.notifyDataSetChanged();
                        }
                        else if (status.equalsIgnoreCase("Absent")){
                            arrayList2.add(name);
                            arrayAdapter2.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        arrayAdapter1.notifyDataSetChanged();
                        arrayAdapter2.notifyDataSetChanged();
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
                present.setAdapter(arrayAdapter1);
                absent.setAdapter(arrayAdapter2);
            }
        });
    }
}