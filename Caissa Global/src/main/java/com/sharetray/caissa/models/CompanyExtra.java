package com.sharetray.caissa.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.sharetray.caissa.global.User;

/**
 * Created by Admin on 10/09/13.
 *
 * this class depends on User model class for SharedPreferences features.
 */
public class CompanyExtra {

    protected int parent;
    protected String title;
    protected int pid;
    protected int status;

    protected String jobTitle;


    Context context;

    protected static final String PREF_FIELD="company_extray";

    public CompanyExtra() {
    }


    public CompanyExtra(Context context) {
        this.context=context;
    }


    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public void setPref(String s){
        SharedPreferences.Editor editor= User.getEditor(context);
        editor.putString(PREF_FIELD,s);
        editor.commit();
    }

    public String getPref(){
        SharedPreferences pref=User.getSharedPreferences(context);
        String s=pref.getString(PREF_FIELD,null);
        return s;
    }
}
