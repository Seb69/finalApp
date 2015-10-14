package com.example.pc.myfirstchat;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by pc on 13/10/2015.
 */
public class Message_Adapteur extends BaseAdapter{

    private List<Message> messages;
    private Context context;

    public Message_Adapteur(List<Message> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int i) {
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.single_message,viewGroup, false);

        TextView login_textview = (TextView) view.findViewById(R.id.login);
        TextView message_textview = (TextView) view.findViewById(R.id.msg);

        Message message = messages.get(i);
        login_textview.setText(message.getLogin());
        message_textview.setText(message.getMessage());

        return view;
    }

    public void addAll(List<Message> messages){
        this.messages = messages;
        this.notifyDataSetChanged();
    }

    public class ViewHolder {
        private TextView pseudo;
        private TextView message;

        public TextView getPseudo() {
            return pseudo;
        }

        public void setPseudo(TextView pseudo) {
            this.pseudo = pseudo;
        }

        public TextView getMessage() {
            return message;
        }

        public void setMessage(TextView message) {
            this.message = message;
        }

        public ViewHolder(TextView pseudo, TextView message) {
            this.pseudo = pseudo;
            this.message = message;
        }
    }
}
