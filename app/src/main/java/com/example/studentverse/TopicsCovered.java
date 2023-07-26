package com.example.studentverse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TopicsCovered extends AppCompatActivity {
    DatabaseReference dref;
    ListView register;
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayAdapter arrayAdapter;
    String replacedmail,mailID,cls,date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics_covered);

        register=findViewById(R.id.register_list);
        arrayAdapter =new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);

        //getting multiple values
        Intent intent = getIntent();
        Bundle extras=intent.getExtras();
        mailID=extras.getString("mail");
        date=extras.getString("date");
        cls=extras.getString("cls");

        //replaced mail
        replacedmail=mailID.replace(".",",");

        dref= FirebaseDatabase.getInstance().getReference().child("Register").child(cls).child("Content").child(date);

        dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String name=snapshot.getKey();
                String sub=snapshot.getValue().toString();
                String resub=sub.replace("{","  ");
                String repsub=resub.replace("="," - ");
                String replacesub=repsub.replace("}"," ");
                arrayList.add(name+" Period  "+replacesub);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                arrayAdapter.notifyDataSetChanged();
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
        register.setAdapter(arrayAdapter);
    }
}