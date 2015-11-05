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

import static android.widget.Toast.makeText;

import static android.widget.Toast.LENGTH_LONG;


public class LoginActivity extends Activity implements ConnectionListenner {


    LoginTask asyncTask =new LoginTask(this);


    private EditText username;
    private EditText password;

    Button b_vider = null;
    Button b_valider = null;
    Button b_register = null;


    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        b_vider = (Button) findViewById(R.id.clear);
        b_valider = (Button) findViewById(R.id.validate);
        b_register = (Button) findViewById(R.id.gotoregister);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);


        b_vider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset username text to empty
                username.setText("");
                password.setText("");
            }

        });

        b_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameStr = username.getText().toString();
                String passwordStr = password.getText().toString();

                // Cancel previous task if it is still running
                if (asyncTask != null && asyncTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
                    asyncTask.cancel(true);
                    restartActivity();
                }
                // Cancel if username  or password field are empty
                if (usernameStr.toString().equals("") || passwordStr.toString().equals("")) {
                    asyncTask.cancel(true);
                    makeText(LoginActivity.this, R.string.Field_missing, LENGTH_LONG).show();
                    restartActivity();
                }
                // Launch Login Task
                progressBar.setVisibility(View.VISIBLE);
                asyncTask.execute(usernameStr, passwordStr);

            }
        });
        b_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionLogregister();

            }
        });


    }

    private void actionLogregister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);

    }

    private void actionLogHomeActivity(){
        Intent intent= new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        //finish();
    }


    @Override
    public void succesProcess() {

        //Write in Preference
        SharedPreferences userDetails = getSharedPreferences("userdetails", MODE_PRIVATE);
        SharedPreferences.Editor edit = userDetails.edit();
        edit.clear();
        edit.putString("username", username.getText().toString().trim());
        edit.putString("password", password.getText().toString().trim());
        edit.commit();

        actionLogHomeActivity();
        finish();

        // Everything good!
        makeText(LoginActivity.this, R.string.login_success, LENGTH_LONG).show();
        progressBar.setVisibility(View.INVISIBLE);
        //finish();

    }

    @Override
    public void failureProcess() {
        makeText(LoginActivity.this, R.string.login_error, LENGTH_LONG).show();
        progressBar.setVisibility(View.INVISIBLE);
        restartActivity();
    }

    @Override
    public void unauthorizedProcess() {
        makeText(LoginActivity.this, R.string.unauthorized, LENGTH_LONG).show();
        progressBar.setVisibility(View.INVISIBLE);
        restartActivity();

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
