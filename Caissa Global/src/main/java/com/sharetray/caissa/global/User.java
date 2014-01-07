package com.sharetray.caissa.global;

import android.content.Context;
import android.content.SharedPreferences;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by Admin on 30/08/13.
 */
public class User {

    public final static String LOGIN_TIME_FIELD ="login_time";

    public final static String LOGIN_CODE_FIELD ="login_code";

    public final static String SESSION_REQUEST_FIELD ="session_query_id";

    public final static String SESSION_RESPONSE_FIELD ="session_query_string";

    public final static String SESSION_ID_FIELD ="session_id";

    public static final String USERNAME_FIELD="email";
    public static final String PASSWORD_FIELD="pass";

    public static final String OS_NAME_FIELD ="device_os";

    public final static String UID_FIELD ="uid";

    public static final String OS_NAME="android";

    public static final String OK ="ok";
    public static final String FAILED ="failed";

    public static final String NO_DATA ="no data";
    public static final String INVALID ="invalid";
    public static final String ACTIVATION_REQUIRED ="activation required";
    public static final String JSON_OBJECT_NAME ="model";

    public static final int OK_CODE =0;
    public static final int FAILED_CODE =1;

    public static final int NO_DATA_CODE =2;
    public static final int INVALID_CODE =3;


    public static final int ACTIVATION_REQUIRED_CODE =4;

    public static final String RESULT ="result";
    public static final String RESULT_CODE ="result code";

    public final static String EMAIL_COUNT="email_count";

    public final static String NOTICE_COUNT="notice_count";

    public final static String PEOPLE_COUNT="people_count";

    public final static String ADVERT_COUNT="advert_count";

    public final static String ARTICLE_COUNT="article_count";

    public final static String PUSH_ID="push_id";
    public final static String PUSH_ID_VALIDITY="push_id_validity";

    public final static String COUNTRY="country";

    protected Context context;

    protected SharedPreferences preferences;

    private  String sharedPreferencesName;

    public User(Context context) {
        this.context=context;
        this.sharedPreferencesName=context.getPackageName();

        this.preferences= context.getSharedPreferences(sharedPreferencesName,Context.MODE_PRIVATE);
    }


    public static User getUser(Context context){
        User user=new User(context);
        return user;
    }

    public static SharedPreferences getSharedPreferences(Context context){
        User user=new User(context);
        return user.preferences;
    }


    public static SharedPreferences.Editor getEditor(Context context){
        User user=new User(context);
        return user.preferences.edit();
    }


    public boolean isLoggedIn(){
       SharedPreferences pref=this.preferences;
       return (pref.getString(LOGIN_CODE_FIELD,null)!=null);

    }


    private static String getAnswer(){

        return "";
    }


    public static ArrayList<NameValuePair> getLoggedData(Context context){
        SharedPreferences pref= User.getSharedPreferences(context);
        String loginCode=pref.getString(LOGIN_CODE_FIELD,"");
        String query=pref.getString(SESSION_REQUEST_FIELD,"");
        int uid=pref.getInt(UID_FIELD,0);
        String answer= User.getAnswer();

        ArrayList<NameValuePair> postData= new ArrayList<NameValuePair>();
        postData.add(new BasicNameValuePair(LOGIN_CODE_FIELD,loginCode));
        postData.add(new BasicNameValuePair(SESSION_REQUEST_FIELD,query));
        postData.add(new BasicNameValuePair(SESSION_RESPONSE_FIELD,answer));
        postData.add(new BasicNameValuePair(UID_FIELD,uid+""));


        return postData;
    }



}
