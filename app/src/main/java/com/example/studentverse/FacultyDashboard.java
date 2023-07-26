package com.example.studentverse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class FacultyDashboard extends AppCompatActivity {
    TextView msgtxt;
    String smail,reqmail;
    FirebaseDatabase fdb;
    DatabaseReference dbref;
    Button logout,takeattendance,timetable,internals,subject;
    Integer status;
    String sub,syllabus,str,cls,selperiod,ncls,ndate,selsem;
    String[] totalhour={"1","2","3","4","5","6","7"};
    String[] totalsem={"I","II","III","IV","V","VI","VII","VIII"};
    final Calendar mycalender= Calendar.getInstance();

    SharedPreferences sharedPreferences;

    private static final String KEY_MAIL="mail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_dashboard);


        msgtxt = findViewById(R.id.msgtxt);
        logout = findViewById(R.id.logout);
        takeattendance = findViewById(R.id.attendence_btn);
        timetable = findViewById(R.id.timetable_btn);
        internals = findViewById(R.id.internals_btn);
        subject = findViewById(R.id.subject_btn);

        sharedPreferences=getSharedPreferences("com.example.studentverse_login_status",MODE_PRIVATE);
        smail=sharedPreferences.getString(KEY_MAIL,null);

        fdb = FirebaseDatabase.getInstance();
        dbref = fdb.getReference();

        reqmail=sharedPreferences.getString(KEY_MAIL,null);
        String welcomemsg="Welcome "+reqmail;
        msgtxt.setText(welcomemsg);

        takeattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });

        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ttdialog();
            }
        });

        internals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intdialog();
            }
        });

        subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subdialog();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FacultyDashboard.this,"Logout Successfully!",Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("status",0);
                editor.apply();
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    //timetable popup for choose class;
    private void ttdialog(){
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("TimeTable");
        LinearLayout layout=new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText clstx=new EditText(this);
        clstx.setInputType(InputType.TYPE_CLASS_TEXT);
        clstx.setHint("Enter the class");  //required
        layout.addView(clstx);
        dialog.setView(layout);

        LinearLayout horizonlayout=new LinearLayout(this);
        horizonlayout.setOrientation(LinearLayout.HORIZONTAL);

        final TextView pertx=new TextView(this);
        pertx.setText("Choose the Semester");
        pertx.setTextSize(18);
        pertx.setTextColor(Color.BLACK);
        horizonlayout.addView(pertx);

        //adapter for dropdown
        final ArrayAdapter<String> adp= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, totalsem);
        final TextView txt=new TextView(this);
        txt.setGravity(Gravity.RIGHT);
        txt.setTextSize(18);
        final Spinner sp1=new Spinner(this);
        sp1.setAdapter(adp);
        sp1.setGravity(Gravity.RIGHT);
        horizonlayout.addView(sp1);
        layout.addView(horizonlayout);

        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cls=clstx.getText().toString().toUpperCase();
                selsem=sp1.getSelectedItem().toString();
                if (TextUtils.isEmpty(cls)){
                    clstx.setError("This field is required");
                    return;
                }
                Intent intent=new Intent(getApplicationContext(),TimeTable.class);
                //sending multiple value from one activity to another
                Bundle extras=new Bundle();
                extras.putString("class",cls);
                extras.putString("sem",selsem);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(FacultyDashboard.this, "You must fill the details for Update Timetable", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        dialog.show();
    }

    //internals popup for choose class;
    private void intdialog(){
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("Internals");
        LinearLayout layout=new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText clstx=new EditText(this);
        clstx.setInputType(InputType.TYPE_CLASS_TEXT);
        clstx.setHint("Enter the class");  //required
        layout.addView(clstx);
        dialog.setView(layout);

        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cls=clstx.getText().toString().toUpperCase();
                if (TextUtils.isEmpty(cls)){
                    clstx.setError("This field is required");
                    return;
                }
                Intent intent=new Intent(getApplicationContext(),Internals.class);
                //sending multiple value from one activity to another
                Bundle extras=new Bundle();
                extras.putString("class",cls);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(FacultyDashboard.this, "You must fill the details for Update Internals", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        dialog.show();
    }

    //internals popup for choose class;
    private void subdialog(){
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("Subjects");
        LinearLayout layout=new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText clstx=new EditText(this);
        clstx.setInputType(InputType.TYPE_CLASS_TEXT);
        clstx.setHint("Enter the class");  //required
        layout.addView(clstx);
        dialog.setView(layout);

        LinearLayout horizonlayout=new LinearLayout(this);
        horizonlayout.setOrientation(LinearLayout.HORIZONTAL);

        final TextView pertx=new TextView(this);
        pertx.setText("Choose the Semester");
        pertx.setTextSize(18);
        pertx.setTextColor(Color.BLACK);
        horizonlayout.addView(pertx);

        //adapter for dropdown
        final ArrayAdapter<String> adp= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, totalsem);
        final TextView txt=new TextView(this);
        txt.setGravity(Gravity.RIGHT);
        txt.setTextSize(18);
        final Spinner sp1=new Spinner(this);
        sp1.setAdapter(adp);
        sp1.setGravity(Gravity.RIGHT);
        horizonlayout.addView(sp1);
        layout.addView(horizonlayout);

        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cls=clstx.getText().toString().toUpperCase();
                selsem=sp1.getSelectedItem().toString();
                if (TextUtils.isEmpty(cls)){
                    clstx.setError("This field is required");
                    return;
                }
                Intent intent=new Intent(getApplicationContext(),Subjects.class);
                //sending multiple value from one activity to another
                Bundle extras=new Bundle();
                extras.putString("class",cls);
                extras.putString("sem",selsem);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(FacultyDashboard.this, "You must fill the details for Update Subjects", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        dialog.show();
    }

    //popup window function
    private void dialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("Attendence");
        LinearLayout layout=new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);


        final EditText clstx=new EditText(this);
        clstx.setInputType(InputType.TYPE_CLASS_TEXT);
        clstx.setHint("Enter the class");  //required
        layout.addView(clstx);


        final EditText period=new EditText(this);
        period.setInputType(InputType.TYPE_CLASS_TEXT);
        period.setHint("Enter the subject");  //required
        layout.addView(period);

        LinearLayout horizonlayout=new LinearLayout(this);
        horizonlayout.setOrientation(LinearLayout.HORIZONTAL);

        final TextView pertx=new TextView(this);
        pertx.setText("Choose the Period");
        pertx.setTextSize(18);
        pertx.setTextColor(Color.BLACK);
        horizonlayout.addView(pertx);

        //adapter for dropdown
        final ArrayAdapter<String> adp= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, totalhour);
        final TextView txt=new TextView(this);
        txt.setGravity(Gravity.RIGHT);
        txt.setTextSize(18);
        final Spinner sp1=new Spinner(this);
        sp1.setAdapter(adp);
        sp1.setGravity(Gravity.RIGHT);
        horizonlayout.addView(sp1);
        layout.addView(horizonlayout);

        final EditText content=new EditText(this);
        content.setInputType(InputType.TYPE_CLASS_TEXT);
        content.setHint("Enter the content to be taken");
        layout.addView(content);
        dialog.setView(layout);
        //add btn to add subject
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selperiod=sp1.getSelectedItem().toString();
                sub=period.getText().toString().toLowerCase();
                cls=clstx.getText().toString().toUpperCase();
                syllabus=content.getText().toString();
                if (TextUtils.isEmpty(cls)){
                    clstx.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(sub)){
                    period.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(syllabus)){
                    period.setError("This field is required");
                    return;
                }
                Intent intent=new Intent(getApplicationContext(),TakeAttendence.class);
                //sending multiple value from one activity to another
                Bundle extras=new Bundle();
                extras.putString("class",cls);
                extras.putString("mail",reqmail);
                extras.putString("period",selperiod);
                extras.putString("subject",sub);
                extras.putString("content",syllabus);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        //cancel btn to cancel the operation
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(FacultyDashboard.this, "You must fill the details to take attendence", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        dialog.show();
    }

}