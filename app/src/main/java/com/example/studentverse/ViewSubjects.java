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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewSubjects extends AppCompatActivity {
    DatabaseReference reference;
    ListView sub,staff;
    TextView sub_txt,staff_txt;
    View error_view;
    Spinner spinner;
    Button fetch;
    FirebaseDatabase firebaseDatabase;
    ArrayList<String> arrayList1=new ArrayList<>();
    ArrayList<String> arrayList2=new ArrayList<>();
    ArrayAdapter arrayAdapter1,arrayAdapter2;
    String sem,cls;
    String[] totalsem={"","I","II","III","IV","V","VI"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_subjects);

        //spinner
        spinner =findViewById(R.id.spinner);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,totalsem);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        sub_txt=findViewById(R.id.sub_textview);
        staff_txt=findViewById(R.id.staff_textview);

        error_view=findViewById(R.id.error);

        sub=findViewById(R.id.sub_list);
        staff=findViewById(R.id.staff_list);
        fetch=findViewById(R.id.fetchbtn);

        firebaseDatabase=FirebaseDatabase.getInstance();
        //getting multiple values
        Intent intent = getIntent();
        Bundle extras=intent.getExtras();
        cls= extras.getString("cls");

        arrayAdapter1 =new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList1);
        arrayAdapter2 =new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList2);

        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList1.clear();
                arrayAdapter1.notifyDataSetChanged();
                arrayList2.clear();
                arrayAdapter2.notifyDataSetChanged();

                sem=spinner.getSelectedItem().toString();
                if(TextUtils.isEmpty(sem)){
                    ((TextView)spinner.getSelectedView()).setError("Error message");
                    error_view.setVisibility(View.VISIBLE);
                    sub_txt.setVisibility(View.GONE);
                    staff_txt.setVisibility(View.GONE);
                    return;
                }

                if(!TextUtils.isEmpty(sem)) {

                    //view head
                    sub_txt.setVisibility(View.VISIBLE);
                    staff_txt.setVisibility(View.VISIBLE);
                    error_view.setVisibility(View.GONE);

                    reference = FirebaseDatabase.getInstance().getReference().child("Register").child(cls).child("Subjects").child(sem);
                    reference.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            String subject = snapshot.getKey();
                            String staff = snapshot.getValue().toString();
                            String resub=staff.replace("{","  ");
                            String repsub=resub.replace("="," - ");
                            String replacesub=repsub.replace("}"," ");
                            arrayList1.add(subject);
                            arrayAdapter1.notifyDataSetChanged();
                            arrayList2.add(replacesub);
                            arrayAdapter2.notifyDataSetChanged();
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
                    sub.setAdapter(arrayAdapter1);
                    staff.setAdapter(arrayAdapter2);
                }
            }
        });
    }
}