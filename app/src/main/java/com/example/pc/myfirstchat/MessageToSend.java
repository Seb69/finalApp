package com.example.pc.myfirstchat;

import java.util.UUID;

/**
 * Created by ANDRE on 13/10/15.
 */
public class MessageToSend {


    private String uuid;
    private String login;
    private String message;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid() {
        UUID uuid2 = UUID.randomUUID();
        uuid = uuid2.toString();
    }
}
