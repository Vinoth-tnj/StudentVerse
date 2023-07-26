package com.example.studentverse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FacultyRegister extends AppCompatActivity {

    TextView login;
    EditText username,mail,password,conpassword;
    Button create_user;
    FirebaseAuth fauth;
    FirebaseDatabase fdb;
    DatabaseReference dbref;
    ProgressBar progressbar;
    static String getname,getmail,getpassword,getconfirmpassword,replacedmail;

    private SharedPreferences sharedPreferences;

    private static final String KEY_MAIL="mail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_register);

        login = findViewById(R.id.loginredir);
        username=findViewById(R.id.username);
        mail=findViewById(R.id.email);
        password=findViewById(R.id.password);
        conpassword=findViewById(R.id.repassword);
        create_user=findViewById(R.id.signupbtn);
        progressbar=findViewById(R.id.progress_bar);

        sharedPreferences=getSharedPreferences("com.example.studentverse_login_status",MODE_PRIVATE);
        boolean status1=sharedPreferences.getBoolean("status1",false);
        Integer status=sharedPreferences.getInt("status",0);
        if (status == 1){
            Intent i=new Intent(FacultyRegister.this,FacultyDashboard.class);
            startActivity(i);
            finish();
        }

        fauth=FirebaseAuth.getInstance();
        fdb=FirebaseDatabase.getInstance();
        dbref=fdb.getReference("faultyusers");

        create_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getname=username.getText().toString().trim();
                getmail=mail.getText().toString().trim();
                getpassword=password.getText().toString().trim();
                getconfirmpassword=conpassword.getText().toString().trim();

                //replace mail
                replacedmail=getmail.replace(".",",");


                if(TextUtils.isEmpty(getname))
                {
                    username.setError("Username is Required");
                    return;
                }
                if(TextUtils.isEmpty(getmail))
                {
                    mail.setError("Mail is Required");
                    return;
                }
                if(TextUtils.isEmpty(getpassword))
                {
                    password.setError("Password is Required");
                    return;
                }
                if (getpassword.length()<8)
                {
                    password.setError("Password must be 8 character");
                    return;
                }
                if (!getconfirmpassword.equals(getpassword)) {
                    conpassword.setError("Password Mismatch");
                    return;
                }

                DataStorage dataStorage=new DataStorage(getname,getmail,getpassword,getconfirmpassword);

                progressbar.setVisibility(View.VISIBLE);

                fauth.createUserWithEmailAndPassword(getmail,getpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //data storing
                            dbref.child(replacedmail).setValue(dataStorage);
                            Toast.makeText(FacultyRegister.this,"Register Successfully!", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putBoolean("status",true);
                            editor.putInt("status",1);
                            editor.putString(KEY_MAIL,getmail);
                            editor.apply();
                            Intent i=new Intent(getApplicationContext(),FacultyLogin.class);
                            //sending multiple value from one activity to another
                            /*Bundle extras=new Bundle();
                            extras.putString("mail",getmail);
                            extras.putString("msg",getname);
                            i.putExtras(extras);*/
                            startActivity(i);
                            finish();
                        }
                        else{
                            Toast.makeText(FacultyRegister.this,"Error! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FacultyLogin.class));
            }
        });
    }
}