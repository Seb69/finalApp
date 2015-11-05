package com.example.pc.myfirstchat;

import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;


public class ListeMessagesActivity extends Activity implements AsyncResponse, ConnectionListenner{

    private final String TAG = LoginActivity.class.getSimpleName();

    Message_Adapteur mAdapter;
    //Messages that we will retrieve
    private List<Message> messages = new ArrayList<>();

    Button refresh = null;
    private ListView listMsg;
    private ProgressBar progressBar;


    ListeMessageTask asyncTask =new ListeMessageTask(this);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_messages);

        refresh = (Button) findViewById(R.id.refresh);
        listMsg = (ListView) findViewById(R.id.listmsg);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_list_msg);

        asyncTask.delegate= this;

        mAdapter = new Message_Adapteur(messages, this);
        listMsg.setAdapter(mAdapter);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //We get username and Password in Shared Preference
                SharedPreferences userDetails = getSharedPreferences("userdetails", MODE_PRIVATE);
                String uname = userDetails.getString("username", "");
                String pass = userDetails.getString("password", "");

                progressBar.setVisibility(View.VISIBLE);
                asyncTask.execute(uname, pass);
            }
        });

    }

    @Override
    public void processFinish(List<Message> output) {
        mAdapter.addAll(output);

    }

    @Override
    public void succesProcess() {

        makeText(ListeMessagesActivity.this, R.string.refresh_success, LENGTH_LONG).show();
        progressBar.setVisibility(View.INVISIBLE);
}

    @Override
    public void failureProcess() {
        makeText(ListeMessagesActivity.this, R.string.message_error, LENGTH_LONG).show();
        progressBar.setVisibility(View.INVISIBLE);
        restartActivity();
    }

    @Override
    public void unauthorizedProcess() {

        makeText(ListeMessagesActivity.this, R.string.unauthorized, LENGTH_LONG).show();
        progressBar.setVisibility(View.INVISIBLE);
        restartActivity();

    }

    public void restartActivity(){

        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        overridePendingTransition(0, 0);
        startActivity(intent);
        finish();
    }



}
