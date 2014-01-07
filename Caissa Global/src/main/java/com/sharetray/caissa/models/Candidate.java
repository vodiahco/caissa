package com.sharetray.caissa.models;

/**
 * Created by Admin on 09/09/13.
 */
public class Candidate {
    public static final String NAME_FIELD="name";
    public static final String OFFICER_FIELD="officer";
    public static final String PID_FIELD="pid";
    public static final String LAST_MODIFIED_FIELD="last_modified";
    public static final String JOB_TITLE_FIELD="job_title";
    public static final String STATUS_FIELD="status";
    public static final String FNAME_FIELD="fname";
    public static final String LNAME_FIELD="lname";
    public static final String COMPANY_ID_FIELD="company_id";


    protected String name;
    protected String fname;
    protected String lname;
    protected String officer;
    protected String lastModified;
    protected String jobTitle;
    protected int status;
    protected int pid;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getOfficer() {
        return officer;
    }

    public void setOfficer(String officer) {
        this.officer = officer;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
