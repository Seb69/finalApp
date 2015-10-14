package com.example.pc.myfirstchat;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;


public class SendMessageActivity extends Activity {

    private EditText message;

    private Button b_send = null;

    SendMessageTask sendMessageTask = new SendMessageTask();

    private static final String API_BASE_URL = "http://training.loicortola.com/chat-rest/1.0/";
    private static final String TAG = LoginActivity.class.getSimpleName();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message2);


        b_send = (Button) findViewById(R.id.sendmsg);
        message = (EditText) findViewById(R.id.msg);


        b_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String messagestr = message.getText().toString();

                // Cancel previous task if it is still running
                if ((sendMessageTask != null) && sendMessageTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
                    sendMessageTask.cancel(true);
                }

                //We get username and Password in Shared Preference
                SharedPreferences userDetails = getSharedPreferences("userdetails", MODE_PRIVATE);
                String Uname = userDetails.getString("username", "");
                String pass = userDetails.getString("password", "");

                // Launch Login Task
                sendMessageTask = new SendMessageTask();
                sendMessageTask.execute(messagestr,Uname,pass);
            }
        });
    }

    private class SendMessageTask extends AsyncTask<String, Void, Boolean> {


        @Override
        protected Boolean doInBackground(String... argz) {
            String mymessage= argz[0];

            //We get username and Password in Shared Preference
            SharedPreferences userDetails = getSharedPreferences("userdetails", MODE_PRIVATE);
            String Uname = userDetails.getString("username", "");
            String pass = userDetails.getString("password", "");



            // Webservice URL
            String url = new StringBuilder(API_BASE_URL)
                    .append("messages/")
                    .append(Uname)
                    .append("/")
                    .append(pass)
                    .toString();
            // Request


            //Json creation
            Gson gson = new Gson();
            //Objet creation
            MessageToSend messageToSend = new MessageToSend();
            messageToSend.setLogin(Uname);
            messageToSend.setMessage(mymessage);
            messageToSend.setUuid();
            String monMsg = gson.toJson(messageToSend);


            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON, monMsg);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                //return response.body().string();
                return response.isSuccessful();
                //return true;
            } catch (IOException e) {
                e.printStackTrace();

            }


            return false;

        }

        @Override
        protected void onPostExecute(Boolean success) {



            // Success
            if (success) {
                makeText(SendMessageActivity.this, R.string.message_success, LENGTH_LONG).show();
                message.setText("");
                return;
            }
            makeText(SendMessageActivity.this, R.string.message_error, LENGTH_LONG).show();
            return;

        }

        @Override protected void onPreExecute() {

            // Reset username text to empty
            // message.setText("");
            // Here, show progress bar

        }
    }

}

