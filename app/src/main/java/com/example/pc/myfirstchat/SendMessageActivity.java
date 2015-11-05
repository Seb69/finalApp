package com.example.pc.myfirstchat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ProgressBar;
import com.squareup.okhttp.MediaType;


import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;


public class SendMessageActivity extends Activity implements ConnectionListenner {

    private EditText message;

    private Button b_send = null;
    private ProgressBar progressBar;


    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    SendMessageTask sendMessageTask = new SendMessageTask(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message2);


        b_send = (Button) findViewById(R.id.sendmsg);
        message = (EditText) findViewById(R.id.msg);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);


        b_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String messagestr = message.getText().toString();

                //We get username and Password in Shared Preference
                SharedPreferences userDetails = getSharedPreferences("userdetails", MODE_PRIVATE);
                String Uname = userDetails.getString("username", "");
                String pass = userDetails.getString("password", "");


                // Cancel previous task if it is still running
                if ((sendMessageTask != null) && sendMessageTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
                    sendMessageTask.cancel(true);
                }

                // Launch Login Task
                progressBar.setVisibility(View.VISIBLE);
                sendMessageTask.execute(messagestr, Uname, pass);
                message.setText("");
            }
        });
    }


    @Override
    public void succesProcess() {
// Here, hide progress bar
        progressBar.setVisibility(View.GONE);
        makeText(SendMessageActivity.this, R.string.message_success, LENGTH_LONG).show();
        restartActivity();

    }

    @Override
    public void failureProcess() {
        progressBar.setVisibility(View.GONE);
        makeText(SendMessageActivity.this, R.string.message_error, LENGTH_LONG).show();
        restartActivity();

    }

    @Override
    public void unauthorizedProcess() {
        progressBar.setVisibility(View.GONE);
        makeText(SendMessageActivity.this, R.string.unauthorized, LENGTH_LONG).show();
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

