package com.sharetray.caissa.models;

/**
 * Created by Admin on 08/09/13.
 */
public class Company {

    public static final String TITLE_FIELD="title";
    public static final String USERNAME_FIELD="username";
    public static final String LAST_MODIFIED_FIELD="last_modified";
    public static final String PID_FIELD="id";
    public static final String STATUS_FIELD="status";

    public static final String START_DATE_FIELD="start_date";
    public static final String END_DATE_FIELD="end_date";
    public static final String TRANSACTION_TIME_FIELD="t_time";

    protected String title;
    protected String username;
    protected String lastModified;
    protected int pid;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
