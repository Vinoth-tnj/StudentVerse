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
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewTimeTable extends AppCompatActivity {
    DatabaseReference reference,ref1;
    ListView sub,staff,timing;
    TextView sub_txt,staff_txt,time_txt;
    Button mon,tue,wed,thu,fri,sat;
    String cls,sem;
    FirebaseDatabase firebaseDatabase;
    ArrayList<String> arrayList1=new ArrayList<>();
    ArrayList<String> arrayList2=new ArrayList<>();
    ArrayList<String> arrayList3=new ArrayList<>();
    ArrayAdapter arrayAdapter1,arrayAdapter2,arrayAdapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_time_table);

        sub=findViewById(R.id.sub_list);
        staff=findViewById(R.id.staff_list);
        timing=findViewById(R.id.timing_list);

        sub_txt=findViewById(R.id.sub_textview);
        staff_txt=findViewById(R.id.staff_textview);
        time_txt=findViewById(R.id.timing_textview);

        mon=findViewById(R.id.monbtn);
        tue=findViewById(R.id.tuebtn);
        wed=findViewById(R.id.wedbtn);
        thu=findViewById(R.id.thubtn);
        fri=findViewById(R.id.fribtn);
        sat=findViewById(R.id.satbtn);

        firebaseDatabase=FirebaseDatabase.getInstance();
        //getting multiple values
        Intent intent = getIntent();
        Bundle extras=intent.getExtras();
        cls= extras.getString("cls");
        sem=extras.getString("sem");

        arrayAdapter1 =new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList1);
        arrayAdapter2 =new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList2);
        arrayAdapter3 =new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList3);

        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList1.clear();
                arrayAdapter1.notifyDataSetChanged();
                arrayList2.clear();
                arrayAdapter2.notifyDataSetChanged();
                arrayList3.clear();
                arrayAdapter3.notifyDataSetChanged();

                //view head
                sub_txt.setVisibility(View.VISIBLE);
                staff_txt.setVisibility(View.VISIBLE);
                time_txt.setVisibility(View.VISIBLE);

                reference = FirebaseDatabase.getInstance().getReference().child("Register").child(cls).child("TimeTable").child(sem).child("Monday");
                reference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String time = snapshot.getKey();
                        String staff_name = snapshot.getValue().toString();
                        //replaced mail
                        String replacedstaff = staff_name.replace("{", "").replace("}", "");
                        String[] separated = replacedstaff.split("=");
                        arrayList1.add(time);
                        arrayList2.add(separated[0]);
                        arrayList3.add(separated[1]);
                        arrayAdapter1.notifyDataSetChanged();
                        arrayAdapter2.notifyDataSetChanged();
                        arrayAdapter3.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        arrayAdapter1.notifyDataSetChanged();
                        arrayAdapter2.notifyDataSetChanged();
                        arrayAdapter3.notifyDataSetChanged();
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

                sub.setAdapter(arrayAdapter2);
                staff.setAdapter(arrayAdapter3);
                timing.setAdapter(arrayAdapter1);
            }
        });
        tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList1.clear();
                arrayAdapter1.notifyDataSetChanged();
                arrayList2.clear();
                arrayAdapter2.notifyDataSetChanged();
                arrayList3.clear();
                arrayAdapter3.notifyDataSetChanged();

                //view head
                sub_txt.setVisibility(View.VISIBLE);
                staff_txt.setVisibility(View.VISIBLE);
                time_txt.setVisibility(View.VISIBLE);

                reference= FirebaseDatabase.getInstance().getReference().child("Register").child(cls).child("TimeTable").child(sem).child("Tuesday");
                reference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String time=snapshot.getKey();
                        String staff_name=snapshot.getValue().toString();
                        //replaced mail
                        String replacedstaff=staff_name.replace("{","").replace("}","");
                        String[] separated = replacedstaff.split("=");
                        arrayList1.add(time);
                        arrayList2.add(separated[0]);
                        arrayList3.add(separated[1]);
                        arrayAdapter1.notifyDataSetChanged();
                        arrayAdapter2.notifyDataSetChanged();
                        arrayAdapter3.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        arrayAdapter1.notifyDataSetChanged();
                        arrayAdapter2.notifyDataSetChanged();
                        arrayAdapter3.notifyDataSetChanged();
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

                sub.setAdapter(arrayAdapter2);
                staff.setAdapter(arrayAdapter3);
                timing.setAdapter(arrayAdapter1);
            }
        });

        wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList1.clear();
                arrayAdapter1.notifyDataSetChanged();
                arrayList2.clear();
                arrayAdapter2.notifyDataSetChanged();
                arrayList3.clear();
                arrayAdapter3.notifyDataSetChanged();

                //view head
                sub_txt.setVisibility(View.VISIBLE);
                staff_txt.setVisibility(View.VISIBLE);
                time_txt.setVisibility(View.VISIBLE);

                reference= FirebaseDatabase.getInstance().getReference().child("Register").child(cls).child("TimeTable").child(sem).child("Wednesday");
                reference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String time=snapshot.getKey();
                        String staff_name=snapshot.getValue().toString();
                        //replaced mail
                        String replacedstaff=staff_name.replace("{","").replace("}","");
                        String[] separated = replacedstaff.split("=");
                        arrayList1.add(time);
                        arrayList2.add(separated[0]);
                        arrayList3.add(separated[1]);
                        arrayAdapter1.notifyDataSetChanged();
                        arrayAdapter2.notifyDataSetChanged();
                        arrayAdapter3.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        arrayAdapter1.notifyDataSetChanged();
                        arrayAdapter2.notifyDataSetChanged();
                        arrayAdapter3.notifyDataSetChanged();
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

                sub.setAdapter(arrayAdapter2);
                staff.setAdapter(arrayAdapter3);
                timing.setAdapter(arrayAdapter1);
            }
        });

        thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList1.clear();
                arrayAdapter1.notifyDataSetChanged();
                arrayList2.clear();
                arrayAdapter2.notifyDataSetChanged();
                arrayList3.clear();
                arrayAdapter3.notifyDataSetChanged();

                //view head
                sub_txt.setVisibility(View.VISIBLE);
                staff_txt.setVisibility(View.VISIBLE);
                time_txt.setVisibility(View.VISIBLE);

                reference= FirebaseDatabase.getInstance().getReference().child("Register").child(cls).child("TimeTable").child(sem).child("Thursday");
                reference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String time=snapshot.getKey();
                        String staff_name=snapshot.getValue().toString();
                        //replaced mail
                        String replacedstaff=staff_name.replace("{","").replace("}","");
                        String[] separated = replacedstaff.split("=");
                        arrayList1.add(time);
                        arrayList2.add(separated[0]);
                        arrayList3.add(separated[1]);
                        arrayAdapter1.notifyDataSetChanged();
                        arrayAdapter2.notifyDataSetChanged();
                        arrayAdapter3.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        arrayAdapter1.notifyDataSetChanged();
                        arrayAdapter2.notifyDataSetChanged();
                        arrayAdapter3.notifyDataSetChanged();
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

                sub.setAdapter(arrayAdapter2);
                staff.setAdapter(arrayAdapter3);
                timing.setAdapter(arrayAdapter1);
            }
        });

        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList1.clear();
                arrayAdapter1.notifyDataSetChanged();
                arrayList2.clear();
                arrayAdapter2.notifyDataSetChanged();
                arrayList3.clear();
                arrayAdapter3.notifyDataSetChanged();

                //view head
                sub_txt.setVisibility(View.VISIBLE);
                staff_txt.setVisibility(View.VISIBLE);
                time_txt.setVisibility(View.VISIBLE);

                reference= FirebaseDatabase.getInstance().getReference().child("Register").child(cls).child("TimeTable").child(sem).child("Friday");
                reference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String time=snapshot.getKey();
                        String staff_name=snapshot.getValue().toString();
                        //replaced mail
                        String replacedstaff=staff_name.replace("{","").replace("}","");
                        String[] separated = replacedstaff.split("=");
                        arrayList1.add(time);
                        arrayList2.add(separated[0]);
                        arrayList3.add(separated[1]);
                        arrayAdapter1.notifyDataSetChanged();
                        arrayAdapter2.notifyDataSetChanged();
                        arrayAdapter3.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        arrayAdapter1.notifyDataSetChanged();
                        arrayAdapter2.notifyDataSetChanged();
                        arrayAdapter3.notifyDataSetChanged();
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

                sub.setAdapter(arrayAdapter2);
                staff.setAdapter(arrayAdapter3);
                timing.setAdapter(arrayAdapter1);
            }
        });

        sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList1.clear();
                arrayAdapter1.notifyDataSetChanged();
                arrayList2.clear();
                arrayAdapter2.notifyDataSetChanged();
                arrayList3.clear();
                arrayAdapter3.notifyDataSetChanged();

                //view head
                sub_txt.setVisibility(View.VISIBLE);
                staff_txt.setVisibility(View.VISIBLE);
                time_txt.setVisibility(View.VISIBLE);

                reference= FirebaseDatabase.getInstance().getReference().child("Register").child(cls).child("TimeTable").child(sem).child("Saturday");
                reference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String time=snapshot.getKey();
                        String staff_name=snapshot.getValue().toString();
                        //replaced mail
                        String replacedstaff=staff_name.replace("{","").replace("}","");
                        String[] separated = replacedstaff.split("=");
                        arrayList1.add(time);
                        arrayList2.add(separated[0]);
                        arrayList3.add(separated[1]);
                        arrayAdapter1.notifyDataSetChanged();
                        arrayAdapter2.notifyDataSetChanged();
                        arrayAdapter3.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        arrayAdapter1.notifyDataSetChanged();
                        arrayAdapter2.notifyDataSetChanged();
                        arrayAdapter3.notifyDataSetChanged();
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

                sub.setAdapter(arrayAdapter2);
                staff.setAdapter(arrayAdapter3);
                timing.setAdapter(arrayAdapter1);
            }
        });
    }
}