package com.example.pc.myfirstchat;

import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
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


public class ListeMessagesActivity extends Activity {

    private final String TAG = LoginActivity.class.getSimpleName();
    private static final String API_BASE_URL = "http://training.loicortola.com/chat-rest/1.0/";

    Message_Adapteur mAdapter;
    //Messages that we will retrieve
    private List<Message> messages = new ArrayList<>();

    Button refresh = null;
    private ListView listMsg;
    private ProgressBar progressBar;
    private MessageTask messageTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_messages);

        refresh = (Button) findViewById(R.id.refresh);
        listMsg = (ListView) findViewById(R.id.listmsg);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_list_msg);


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageTask = new MessageTask();
                messageTask.execute();
            }
        });
        Object testMesg;
        mAdapter = new Message_Adapteur(messages, this);
        listMsg.setAdapter(mAdapter);
    }

    private class MessageTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            // Here, show progress bar
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(String... params) {

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
                // FIXME to be removed. Simulates heavy network workload
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
                List<Message> messages = gson.fromJson(jsonMessages, type);

            } catch (IOException e) {
                Log.w(TAG, "Exception occured while logging in: " + e.getMessage());
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            // Here, hide progress bar and proceed to login if OK.
            progressBar.setVisibility(View.GONE);

            // Wrong login entered
            if (!success) {
                makeText(ListeMessagesActivity.this, R.string.refresh_error, LENGTH_LONG).show();
                return;
            }
            makeText(ListeMessagesActivity.this, R.string.refresh_success, LENGTH_LONG).show();


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_liste_messages, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
