package com.ateots.example4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    EditText etId, etPassword;
    Button btnLogin, btnRegister;
    String strPassword;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnRegister = (Button)findViewById(R.id.btnRegister);

        etId = (EditText)findViewById(R.id.etId);
        etPassword =(EditText)findViewById(R.id.etPassword);

        progressBar =(ProgressBar)findViewById(R.id.progressbar);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = etId.getText().toString();
                strPassword = etPassword.getText().toString();

                if(strEmail.isEmpty()){
                    Toast.makeText(MainActivity.this,"Insert Email",Toast.LENGTH_LONG).show();
                }if(strPassword.isEmpty()){
                    Toast.makeText(MainActivity.this,"Insert PassWord",Toast.LENGTH_LONG).show();
                }

                progressBar.setVisibility(View.VISIBLE);

               mAuth.signInWithEmailAndPassword(strEmail, strPassword)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    String stUserEmail = user.getEmail();
                                    //String stUserName = user.getDisplayName();

                                    Log.d(TAG, "stUserEmail: " + stUserEmail);
                                    //Log.d(TAG, "stUserName: " + stUserName);

                                    Intent intent = new Intent(MainActivity.this, ChattingActivity.class);
                                    intent.putExtra("email", strEmail);
                                    startActivity(intent);
                                    // updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    // updateUI(null);
                                }
                            }
                        });

                //Toast.makeText(MainActivity.this,"Login",Toast.LENGTH_LONG).show();

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = etId.getText().toString();
                strPassword = etPassword.getText().toString();
                if(strEmail.isEmpty()){
                    Toast.makeText(MainActivity.this,"Insert Email",Toast.LENGTH_LONG).show();
                }if(strPassword.isEmpty()){
                    Toast.makeText(MainActivity.this,"Insert PassWord",Toast.LENGTH_LONG).show();
                }
                // Toast.makeText(MainActivity.this,"Email" + strEmail,Toast.LENGTH_LONG).show();
                mAuth.createUserWithEmailAndPassword(strEmail, strPassword)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    // updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    // updateUI(null);
                                }

                                // ...
                            }
                        });
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
}