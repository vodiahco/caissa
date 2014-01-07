package com.sharetray.caissa.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Victor Odiah on 29/08/13.
 */
public class Session implements Parcelable {

    protected String loginTime;
    protected String loginCode;

    protected int queryId;
    protected String queryString;

    protected String sessionId;

    protected int uid;

    public Session(Parcel p) {
        readFromParcel(p);
    }


    public void readFromParcel(Parcel p) {
        loginCode=p.readString();
        loginTime=p.readString();
        queryId=p.readInt();
        queryString=p.readString();
    }

    public final static Creator<Session> CREATOR =new Creator<Session>(){

        public Session createFromParcel(Parcel p){
            return new Session(p);
        }

        public Session[] newArray(int size){
            return new Session[size];
        }

    };



    public String getLoginTime() {
        return loginTime;
    }


    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public int getQueryId() {
        return queryId;
    }

    public void setQueryId(int queryId) {
        this.queryId = queryId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(loginCode);
        dest.writeString(loginTime);
        dest.writeInt(queryId);
        dest.writeString(queryString);
    }





}
