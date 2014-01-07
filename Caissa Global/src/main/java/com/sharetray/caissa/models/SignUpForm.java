package com.sharetray.caissa.models;

import android.widget.EditText;
import android.widget.RadioGroup;

/**
 * Created by Victor Odiah on 24/07/13.
 */
public class SignUpForm {

    protected String email;
    protected String pass;
    protected String cpass;
    protected String sex;
    protected String fname;
    protected String lname;

    protected String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public SignUpForm(String email, String pass, String cpass, String sex, String fname, String lname) {
        this.pass = pass;
        this.email = email;
        this.cpass=cpass;
        this.sex=sex;
        this.fname=fname;
        this.lname=lname;

    }

    public SignUpForm(EditText email, EditText pass, EditText cpass, RadioGroup sex, EditText fname, EditText lname){

    }

    public String getCpass() {
        return cpass;
    }

    public void setCpass(String cpass) {
        this.cpass = cpass;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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
       if(fname.equalsIgnoreCase("")|| fname.length()<1){
            errorMessage="enter first name";
            return false;
        }

        else if(lname.equalsIgnoreCase("")|| lname.length()<1){
            errorMessage="enter last name";
            return false;
        }
       else if(sex.equalsIgnoreCase("")){
           errorMessage="select your gender";
           return false;
       }
       else if(email.length()<5){
            errorMessage="Please enter a valid email";
            return false;
        }

        else if(pass.length()<6){
            errorMessage="password must be more that six characters";
            return false;
        }
        else if(!cpass.equals(pass)){
            errorMessage="Password must match";
            return false;
        }

        else
        return true;
    }




}
