package com.example.pc.myfirstchat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends Activity {

    Button listMsgBtn= null;
    Button sendMsgBtn= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listMsgBtn = (Button) findViewById(R.id.listmsg);
        sendMsgBtn = (Button) findViewById(R.id.sendmsg);

       listMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionLogListmsg();
            }
        });

        sendMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionLogsendmsg();
            }
        });
    }

    private void actionLogListmsg (){
        Intent intent= new Intent(this, ListeMessagesActivity.class);
        startActivity(intent);
    }

    private void actionLogsendmsg(){
        Intent intent= new Intent(this, SendMessageActivity.class);
        startActivity(intent);
    }

}
