package com.example.neeraj.firebaseauth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.PriorityQueue;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btLogin;
    private EditText etLoginEmail, etLoginPass;
    private TextView tvSignUp;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            //start profile activity directly
            finish();
            startActivity(new Intent(this,ProfileActivity.class));
        }
        progressDialog=new ProgressDialog(this);
        btLogin=findViewById(R.id.btLogin);
        etLoginEmail= findViewById(R.id.etLoginEmail);
        etLoginPass= findViewById(R.id.etLoginPass);
        tvSignUp= findViewById(R.id.tvSignUp);


        btLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
    }

    private void userLogin(){
        String email=etLoginEmail.getText().toString().trim();
        String password=etLoginPass.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show();
            return; //stopping the function from executing further

        }

        if(TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        //if validations are ok we will show a progress bar
        progressDialog.setMessage("Logging User...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Log In Successfull !!", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(LoginActivity.this,ProfileActivity.class));
                        }else{
                            Toast.makeText(LoginActivity.this, "Log In Failed. Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view==btLogin){
            userLogin();
        }
        if(view==tvSignUp){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }
}
