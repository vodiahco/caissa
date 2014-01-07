package com.sharetray.caissa.net;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LoadUrlData {

    InputStream is = null;
    String result = "";
    protected HttpEntity formData;
    protected String url;
    protected String dispatchMethod;

    public LoadUrlData(String url,HttpEntity formData) {
        this.formData = formData;
        this.url = url;
    }

    public LoadUrlData(String url,HttpEntity formData,String dispatchMethod) {
        this.formData = formData;
        this.url = url;
        this.dispatchMethod=dispatchMethod;
    }

    public LoadUrlData(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpEntity getFormData() {
        return formData;
    }

    public void setFormData(HttpEntity formData) {
        this.formData = formData;
    }

    public String getTargetData(){



        // HTTP
        try {
            HttpClient httpclient = new DefaultHttpClient(); // for port 80 requests!

            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(formData);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();




        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        // Read response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }



        return result;

    }




    public String getUrlData(){



        // HTTP
        try {
            HttpClient httpclient = new DefaultHttpClient(); // for port 80 requests!

            HttpGet httpget = new HttpGet(url);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();




        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        // Read response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }



        return result;

    }
}
