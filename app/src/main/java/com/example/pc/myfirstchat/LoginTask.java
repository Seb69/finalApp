package com.example.pc.myfirstchat;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.http.HttpStatus;

import java.io.IOException;

import static android.widget.Toast.makeText;

/**
 * Created by ANDRE on 14/10/15.
 */
public class LoginTask extends AsyncTask<String, Void, Integer> {

    private static final String API_BASE_URL = "http://training.loicortola.com/chat-rest/1.0/";
    private static final String TAG = "LoginTask";


    private ConnectionListenner connectionListenner;

    public LoginTask(ConnectionListenner connectionListenner) {
        this.connectionListenner = connectionListenner;
    }


    @Override
    protected Integer doInBackground(String... params) {
        String username = params[0];
        String password = params[1];

        // Here, call the login webservice
        //HttpClient client = new DefaultHttpClient();
        OkHttpClient client = new OkHttpClient();

        // Webservice URL
        String url = new StringBuilder(API_BASE_URL + "/connect/")
                .append(username)
                .append("/")
                .append(password)
                .toString();
        // Webservice URL

        // Request
        Request request = new Request.Builder()
                .url(url)
                .build();



        try {

            Response response = client.newCall(request).execute();
            //String status = response.body().string();
            int responseCode = response.code();
            return responseCode;

        } catch (IOException e) {
            Log.w(TAG, "Exception occured while logging in: " + e.getMessage());

            return 0;
        }

    }



    @Override
    protected void onPostExecute(Integer StatusCode) {

        if (StatusCode == HttpStatus.SC_OK) {
            connectionListenner.succesProcess();

        }
        if (StatusCode == HttpStatus.SC_UNAUTHORIZED)
        {
            connectionListenner.failureProcess();

        }


    }

}
