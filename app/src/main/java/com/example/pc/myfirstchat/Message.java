package com.example.pc.myfirstchat;

/**
 * Created by pc on 13/10/2015.
 */
public class Message {

    private String u_id;
    private String login;
    private String message;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
