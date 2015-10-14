package com.example.pc.myfirstchat;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

import static android.widget.Toast.makeText;

/**
 * Created by ANDRE on 14/10/15.
 */
public class RegisterTask extends AsyncTask<String, Void, Integer> {


    private static final String TAG ="RegisterTask";
    private static final String API_BASE_URL = "http://training.loicortola.com/chat-rest/1.0/";

    private ConnectionListenner connectionListenner;

    public RegisterTask(ConnectionListenner connectionListenner) {
        this.connectionListenner = connectionListenner;
    }

    @Override
    protected Integer doInBackground(String... params) {

        String login = params[0];
        String password = params[1];

        // Here, call the login webservice
        HttpClient client = new DefaultHttpClient();

        // Webservice URL
        String url = new StringBuilder(API_BASE_URL + "/register/")
                .append(login)
                .append("/")
                .append(password)
                .toString();



        HttpPost loginRequest = new HttpPost(url);


        HttpResponse response = null;
        try {
            response = client.execute(loginRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return response.getStatusLine().getStatusCode();

    }



    @Override
    protected void onPostExecute(Integer StatusCode) {

        if (StatusCode == HttpStatus.SC_OK) {
            connectionListenner.succesProcess();

        }
        if (StatusCode == HttpStatus.SC_BAD_REQUEST)
        {
            connectionListenner.failureProcess();

        }


    }
}

