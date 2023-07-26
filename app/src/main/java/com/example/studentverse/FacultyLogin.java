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

public class FacultyLogin extends AppCompatActivity {

    EditText loginmail,loginpassword;
    FirebaseAuth fauth;
    Button loginbtn;
    TextView register;
    ProgressBar progressbar;

    private SharedPreferences sharedPreferences;

    private static final String KEY_MAIL="mail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_login);

        loginmail=findViewById(R.id.email);
        loginpassword=findViewById(R.id.password);
        register=findViewById(R.id.regisredir);
        loginbtn=findViewById(R.id.loginbtn);
        fauth= FirebaseAuth.getInstance();
        progressbar=findViewById(R.id.progress_bar);

        sharedPreferences=getSharedPreferences("com.example.studentverse_login_status",MODE_PRIVATE);
        boolean status1=sharedPreferences.getBoolean("status1",false);
        Integer status=sharedPreferences.getInt("status",0);
        if (status == 1){
            Intent i=new Intent(FacultyLogin.this,FacultyDashboard.class);
            startActivity(i);
            finish();
        }

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lmail=loginmail.getText().toString().trim();
                String lpassword=loginpassword.getText().toString().trim();

                if(TextUtils.isEmpty(lmail))
                {
                    loginmail.setError("Mail is Required");
                    return;
                }
                if(TextUtils.isEmpty(lpassword))
                {
                    loginpassword.setError("Password is Required");
                    return;
                }
                if (lpassword.length()<8)
                {
                    loginpassword.setError("Password must be 8 character");
                    return;
                }
                progressbar.setVisibility(View.VISIBLE);

                fauth.signInWithEmailAndPassword(lmail,lpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(FacultyLogin.this,"Login Successfully!",Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putBoolean("status",true);
                            editor.putInt("status",1);
                            editor.putString(KEY_MAIL,lmail);
                            editor.apply();
                            Intent i=new Intent(getApplicationContext(),FacultyDashboard.class);
                            //sending multiple value from one activity to another
                            /*Bundle extras=new Bundle();
                            extras.putString("mail",lmail);
                            extras.putString("msg","back");
                            i.putExtras(extras);*/
                            startActivity(i);
                            finish();
                        }
                        else{
                            Toast.makeText(FacultyLogin.this,"Error! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FacultyRegister.class));
                finish();
            }
        });
    }
}