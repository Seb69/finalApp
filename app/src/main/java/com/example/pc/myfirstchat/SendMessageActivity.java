package com.example.pc.myfirstchat;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

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
            }
        });
    }


    @Override
    public void succesProcess() {
// Here, hide progress bar
        progressBar.setVisibility(View.GONE);
        makeText(SendMessageActivity.this, R.string.message_success, LENGTH_LONG).show();
    }

    @Override
    public void failureProcess() {
        progressBar.setVisibility(View.GONE);
        makeText(SendMessageActivity.this, R.string.message_error, LENGTH_LONG).show();

    }

    @Override
    public void unauthorizedProcess() {

    }
}

