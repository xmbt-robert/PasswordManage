package com.fx.passform.plugin;


import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Created by fan.xu on 2014/10/24.
 */
public class MyAuthenticator extends Authenticator {
    String userName=null;
    String password=null;

    public MyAuthenticator(){
    }
    public MyAuthenticator(String username, String password) {
        this.userName = username;
        this.password = password;
    }
    protected PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(userName, password);
    }
}