package com.example.pc.myfirstchat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.lang.Override;import java.lang.String;

import static android.widget.Toast.makeText;

import static android.widget.Toast.LENGTH_LONG;

public class RegisterActivity extends Activity implements ConnectionListenner {

    private static final String TAG = RegisterActivity.class.getSimpleName();


    RegisterTask registerTask =new RegisterTask(this);

    private EditText username;
    private EditText password;
    Button b_reset = null;
    Button b_register = null;
    private ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        b_reset = (Button) findViewById(R.id.reset);
        b_register = (Button) findViewById(R.id.Register);
        username = (EditText) findViewById(R.id.FName);
        password = (EditText) findViewById(R.id.RegisterPass);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);


        b_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset username text to empty
                username.setText("");
                password.setText("");
            }

        });

        b_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameStr = username.getText().toString();
                String passwordStr = password.getText().toString();

              /*  Intent intent = new Intent(Login.this, Home.class);
                startActivity(intent);
                finish();
                */

                // Cancel previous task if it is still running
                if (registerTask != null && registerTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
                    registerTask.cancel(true);
                }
                // Launch Login Task
                progressBar.setVisibility(View.VISIBLE);

                registerTask.execute(usernameStr, passwordStr);

            }
        });
    }

    @Override
    public void succesProcess( ) {


        // Here, hide progress bar
        progressBar.setVisibility(View.GONE);



        SharedPreferences userDetails = getSharedPreferences("userdetails", MODE_PRIVATE);
        SharedPreferences.Editor edit = userDetails.edit();
        edit.clear();
        edit.putString("username", username.getText().toString().trim());
        edit.putString("password", password.getText().toString().trim());
        edit.commit();


        finish();
        actionLogHome();

        // Everything good!
        makeText(RegisterActivity.this, R.string.register_success, LENGTH_LONG).show();

    }

    @Override
    public void failureProcess() {

        // Here, hide progress bar

        makeText(RegisterActivity.this, R.string.register_error, LENGTH_LONG).show();
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.GONE);

        username.setText("");
        password.setText("");
        restartActivity();
    }

    @Override
    public void unauthorizedProcess() {

        makeText(RegisterActivity.this, R.string.unauthorized, LENGTH_LONG).show();
        progressBar.setVisibility(View.INVISIBLE);
        username.setText("");
        password.setText("");
        restartActivity();

    }

    private void actionLogHome (){
        Intent intent= new Intent(this, HomeActivity.class);
        startActivity(intent);
    }


    public void restartActivity(){
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);

    }
}
