package com.sharetray.caissa.models;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by Victor Odiah on 24/07/13.
 */
public class LoginForm {

    protected String email;
    protected String pass;
    protected String errorMessage;



    public String getErrorMessage() {
        return errorMessage;
    }

    public LoginForm(String email, String pass) {
        this.pass = pass;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean validate(){
        if(email.length()<5)
            errorMessage="Please enter a valid email";
        else if(pass.length()<6)
            errorMessage="Please enter a valid password";
        return(email.length()>4 && pass.length()>5);
    }

    public ArrayList<NameValuePair> getFormData(){
        ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
        data.add(new BasicNameValuePair("email",email));
        data.add(new BasicNameValuePair("pass",pass));
        return data;

    }



}
