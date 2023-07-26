package com.example.studentverse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class StudentDashboard extends AppCompatActivity {
    TextView msgtxt;
    String smail,reqmail;
    Button logout,view_atten,view_topics,view_tt,view_internals,view_sub;
    FirebaseDatabase fdb;
    DatabaseReference dbref;
    String sub,syllabus,str,cls,selperiod,ndate,selsem;
    String[] totalhour={"1","2","3","4","5","6","7"};
    String[] totalsem={"I","II","III","IV","V","VI","VII","VIII"};
    final Calendar mycalender= Calendar.getInstance();

    SharedPreferences sharedPreferences;

    private static final String KEY_MAIL="mail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        msgtxt = findViewById(R.id.msgtxt);
        logout = findViewById(R.id.logout);
        view_atten = findViewById(R.id.attendence_btn);
        view_tt = findViewById(R.id.timetable_btn);
        view_topics = findViewById(R.id.topics_btn);
        view_internals = findViewById(R.id.internals_btn);
        view_sub = findViewById(R.id.subjects_btn);

        sharedPreferences=getSharedPreferences("com.example.studentverse_login_status",MODE_PRIVATE);
        smail=sharedPreferences.getString(KEY_MAIL,null);

        fdb = FirebaseDatabase.getInstance();
        dbref = fdb.getReference();

        reqmail=sharedPreferences.getString(KEY_MAIL,null);
        String welcomemsg="Welcome "+reqmail;
        msgtxt.setText(welcomemsg);

        view_atten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_attendence_dialog();
            }
        });

        view_tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_tt_dialog();
            }
        });

        view_topics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_topics_dialog();
            }
        });

        view_internals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_internal_dialog();
            }
        });

        view_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_subject_dialog();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StudentDashboard.this,"Logout Successfully!",Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("status",0);
                editor.apply();
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void view_attendence_dialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("View Attendence");
        LinearLayout layout=new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText clstx=new EditText(this);
        clstx.setInputType(InputType.TYPE_CLASS_TEXT);
        clstx.setHint("Enter Class");  //required
        clstx.setTextColor(Color.BLACK);
        layout.addView(clstx);

        final EditText mdate=new EditText(this);
        mdate.setInputType(InputType.TYPE_CLASS_TEXT);
        mdate.setHint("Date");  //required
        mdate.setClickable(false);
        mdate.setFocusable(false);
        mdate.setCursorVisible(false);
        mdate.setFocusableInTouchMode(false);
        layout.addView(mdate);

        DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mycalender.set(Calendar.YEAR,year);
                mycalender.set(Calendar.MONTH,month);
                mycalender.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat="dd-MMM-yyyy";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                mdate.setText(dateFormat.format(mycalender.getTime()));
            }
        };

        mdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(StudentDashboard.this,date,mycalender.get(Calendar.YEAR),mycalender.get(Calendar.MONTH),mycalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        dialog.setView(layout);
        //add btn to add subject
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cls=clstx.getText().toString().toUpperCase();
                ndate=mdate.getText().toString();
                //nhr=sp1.getSelectedItem().toString();
                if (TextUtils.isEmpty(ndate)){
                    mdate.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(cls)){
                    clstx.setError("This field is required");
                    return;
                }
                Intent intent=new Intent(getApplicationContext(),ViewAttendence.class);
                //sending multiple value from one activity to another
                Bundle extras=new Bundle();
                extras.putString("mail",reqmail);
                extras.putString("date",ndate);
                extras.putString("cls",cls);
                // extras.putString("hr",nhr);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        //cancel btn to cancel the operation
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(StudentDashboard.this, "You must fill the details to View attendence", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void view_tt_dialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("View TimeTable");
        LinearLayout layout=new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText clstx=new EditText(this);
        clstx.setInputType(InputType.TYPE_CLASS_TEXT);
        clstx.setHint("Enter Class");  //required
        clstx.setTextColor(Color.BLACK);
        layout.addView(clstx);

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

        dialog.setView(layout);
        //add btn to add subject
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cls=clstx.getText().toString().toUpperCase();
                selsem=sp1.getSelectedItem().toString();
                //nhr=sp1.getSelectedItem().toString();
                if (TextUtils.isEmpty(cls)){
                    clstx.setError("This field is required");
                    return;
                }
                Intent intent=new Intent(getApplicationContext(),ViewTimeTable.class);
                //sending multiple value from one activity to another
                Bundle extras=new Bundle();
                extras.putString("cls",cls);
                extras.putString("sem",selsem);
                // extras.putString("hr",nhr);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        //cancel btn to cancel the operation
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(StudentDashboard.this, "You must fill the details to View TimeTable", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void view_topics_dialog(){
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("View Register");
        LinearLayout layout=new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout horilayout=new LinearLayout(this);
        horilayout.setOrientation(LinearLayout.HORIZONTAL);

        final EditText clstx=new EditText(this);
        clstx.setInputType(InputType.TYPE_CLASS_TEXT);
        clstx.setHint("Enter Class");  //required
        clstx.setTextColor(Color.BLACK);
        layout.addView(clstx);

        final EditText mdate=new EditText(this);
        mdate.setInputType(InputType.TYPE_CLASS_TEXT);
        mdate.setHint("Date");  //required
        mdate.setClickable(false);
        mdate.setFocusable(false);
        mdate.setCursorVisible(false);
        mdate.setFocusableInTouchMode(false);
        layout.addView(mdate);

        DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mycalender.set(Calendar.YEAR,year);
                mycalender.set(Calendar.MONTH,month);
                mycalender.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat="dd-MMM-yyyy";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                mdate.setText(dateFormat.format(mycalender.getTime()));
            }
        };
        mdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(StudentDashboard.this,date,mycalender.get(Calendar.YEAR),mycalender.get(Calendar.MONTH),mycalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        dialog.setView(layout);
        //add btn to add subject
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cls=clstx.getText().toString().toUpperCase();
                ndate=mdate.getText().toString();
                //nhr=sp1.getSelectedItem().toString();
                if (TextUtils.isEmpty(cls)){
                    clstx.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(ndate)){
                    mdate.setError("This field is required");
                    return;
                }
                Intent intent=new Intent(getApplicationContext(),TopicsCovered.class);
                //sending multiple value from one activity to another
                Bundle extras=new Bundle();
                extras.putString("mail",reqmail);
                extras.putString("date",ndate);
                extras.putString("cls",cls);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        //cancel btn to cancel the operation
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(StudentDashboard.this, "You must fill the details to View Register", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        dialog.show();
    }
    private void view_internal_dialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("View Internals");
        LinearLayout layout=new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText clstx=new EditText(this);
        clstx.setInputType(InputType.TYPE_CLASS_TEXT);
        clstx.setHint("Enter Class");  //required
        clstx.setTextColor(Color.BLACK);
        layout.addView(clstx);

        dialog.setView(layout);
        //add btn to add subject
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cls=clstx.getText().toString().toUpperCase();
                //nhr=sp1.getSelectedItem().toString();
                if (TextUtils.isEmpty(cls)){
                    clstx.setError("This field is required");
                    return;
                }
                Intent intent=new Intent(getApplicationContext(),ViewInternals.class);
                //sending multiple value from one activity to another
                Bundle extras=new Bundle();
                extras.putString("cls",cls);
                // extras.putString("hr",nhr);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        //cancel btn to cancel the operation
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(StudentDashboard.this, "You must fill the details to View Internals", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void view_subject_dialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("View Subjects");
        LinearLayout layout=new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText clstx=new EditText(this);
        clstx.setInputType(InputType.TYPE_CLASS_TEXT);
        clstx.setHint("Enter Class");  //required
        clstx.setTextColor(Color.BLACK);
        layout.addView(clstx);

        dialog.setView(layout);
        //add btn to add subject
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cls=clstx.getText().toString().toUpperCase();
                //nhr=sp1.getSelectedItem().toString();
                if (TextUtils.isEmpty(cls)){
                    clstx.setError("This field is required");
                    return;
                }
                Intent intent=new Intent(getApplicationContext(),ViewSubjects.class);
                //sending multiple value from one activity to another
                Bundle extras=new Bundle();
                extras.putString("cls",cls);
                // extras.putString("hr",nhr);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        //cancel btn to cancel the operation
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(StudentDashboard.this, "You must fill the details to View Subjects", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        dialog.show();
    }
}