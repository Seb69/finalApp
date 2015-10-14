package com.example.pc.myfirstchat;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.apache.http.HttpStatus;

import java.io.IOException;

/**
 * Created by ANDRE on 14/10/15.
 */
public class SendMessageTask extends AsyncTask<String, Void, Integer> {

    private static final String API_BASE_URL = "http://training.loicortola.com/chat-rest/1.0/";
    private static final String TAG = LoginActivity.class.getSimpleName();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");



    private ConnectionListenner connectionListenner;

    public SendMessageTask(ConnectionListenner connectionListenner) {
        this.connectionListenner = connectionListenner;
    }


    @Override
    protected Integer doInBackground(String... argz) {


        String mymessage= argz[0];
        String username = argz[1];
        String password = argz[2];



        // Webservice URL
        String url = new StringBuilder(API_BASE_URL)
                .append("messages/")
                .append(username)
                .append("/")
                .append(password)
                .toString();
        // Request


        //Json creation
        Gson gson = new Gson();
        //Objet creation
        MessageToSend messageToSend = new MessageToSend();
        messageToSend.setLogin(username);
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
            Integer responsecode =response.code();
            return responsecode;
            //return true;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
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

        if (StatusCode == HttpStatus.SC_UNAUTHORIZED)
        {
            connectionListenner.unauthorizedProcess();

        }


    }
}
