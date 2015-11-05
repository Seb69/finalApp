package com.example.pc.myfirstchat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends Activity {

    Button listMsgBtn= null;
    Button sendMsgBtn= null;
    Button b_logout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listMsgBtn = (Button) findViewById(R.id.listmsg);
        sendMsgBtn = (Button) findViewById(R.id.sendmsg);
        b_logout = (Button)  findViewById(R.id.logout);

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

        b_logout.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                //Write in Preference
                SharedPreferences userDetails = getSharedPreferences("userdetails", MODE_PRIVATE);
                SharedPreferences.Editor edit = userDetails.edit();
                edit.clear();
                finish();
                actionLogOut();
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
    private void actionLogOut(){
        Intent intent= new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
