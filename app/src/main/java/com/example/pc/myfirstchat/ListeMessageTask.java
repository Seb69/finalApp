package com.example.pc.myfirstchat;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.http.HttpStatus;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

/**
 * Created by ANDRE on 03/11/15.
 */
public class ListeMessageTask extends AsyncTask<String, Void,List<Message>> {

    private static final String API_BASE_URL = "http://training.loicortola.com/chat-rest/1.0/";
    private static final String TAG = ListeMessageTask.class.getSimpleName();

    private ConnectionListenner connectionListenner;

    //Messages that we will retrieve
    private List<Message> messages = new ArrayList<>();

    public AsyncResponse delegate = null;//Call back interface



    public ListeMessageTask(ConnectionListenner connectionListenner) {
        this.connectionListenner = connectionListenner;
    }






    @Override
    protected  List<Message> doInBackground(String... params) {

        String username = params[0];
        String password = params[1];

        // Here, call the login webservice
        OkHttpClient client = new OkHttpClient();

        // Webservice URL
        String url = new StringBuilder(API_BASE_URL + "/messages/")
                .append(username)
                .append("/")
                .append(password)
                .toString();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Request
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            String jsonMessages = response.body().string();

            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Message>>() {
            }.getType();

            messages = gson.fromJson(jsonMessages, type);


            return messages;

        } catch (IOException e) {
            Log.w(TAG, "Exception occured while logging in: " + e.getMessage());
        }

        return null;
    }


    @Override
    protected void onPostExecute(List<Message> output) {


        if (delegate != null) {
            //return success or fail to activity
            delegate.processFinish(output);
        }

        connectionListenner.succesProcess();

    }


}
