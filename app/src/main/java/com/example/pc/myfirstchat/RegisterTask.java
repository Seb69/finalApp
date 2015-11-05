package com.example.pc.myfirstchat;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

import okio.BufferedSink;

import static android.widget.Toast.makeText;

/**
 * Created by ANDRE on 14/10/15.
 */
public class RegisterTask extends AsyncTask<String, Void, Integer> {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private static final String TAG = RegisterTask.class.getSimpleName();
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
        OkHttpClient client = new OkHttpClient();

        // Webservice URL
        String url = new StringBuilder(API_BASE_URL + "/register/")
                .append(login)
                .append("/")
                .append(password)
                .toString();

        RequestBody body = RequestBody.create(JSON, "");


        // Request
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();



        try {

            Response response = client.newCall(request).execute();
            return response.code();


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }




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

