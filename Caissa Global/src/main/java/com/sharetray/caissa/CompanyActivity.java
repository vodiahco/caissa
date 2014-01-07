package com.sharetray.caissa;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sharetray.caissa.adapter.CandidateAdapter;
import com.sharetray.caissa.dialogs.PostSuccessActionDialog;
import com.sharetray.caissa.global.DrawerToggle;
import com.sharetray.caissa.global.User;
import com.sharetray.caissa.models.Candidate;
import com.sharetray.caissa.models.Company;
import com.sharetray.caissa.net.LoadUrlData;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class CompanyActivity extends ASActivity implements View.OnClickListener {

    private static final String URL=WEBSITE+"/mobile/candidate/index/";

    private static final String URL_ADD=WEBSITE+"/mobile/candidate/create";


    private String urlWithId;
    protected DrawerLayout mDrawerLayout;
    protected DrawerToggle mDrawerToggle;
    Activity thisApp;
    ImageView imageButtonAdd;
    TextView txtTitle;

    LinearLayout layForm;
    ImageView imgAdd;
    EditText inpFName,inpLName,inpJobTitle;
    Button btnAdd;

    String fName;
    String jobTitle;
    int companyId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        Bundle bundle= getIntent().getExtras();
        String title=bundle.getString(Company.TITLE_FIELD);
        companyId=bundle.getInt(Company.PID_FIELD);
        urlWithId=URL+"id/"+companyId;

        initComponents();
        initListeners();

        txtTitle.setText(title);

        thisApp=this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadPageContentFromWeb();

    }

    @Override
    protected void initComponents() {
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_main);
//        mDrawerToggle = new DrawerToggle(this, mDrawerLayout,
//                R.drawable.ic_drawer, R.string.app_name, R.string.app_name);

        layForm= (LinearLayout) findViewById(R.id.lay_form);
        imgAdd= (ImageView) findViewById(R.id.img_add);



        imageButtonAdd= (ImageView) findViewById(R.id.img_add);
        txtTitle= (TextView) findViewById(R.id.txt_title);
        inpFName= (EditText) findViewById(R.id.inp_fname);
        inpJobTitle=(EditText) findViewById(R.id.inp_job_title);
        btnAdd=(Button) findViewById(R.id.btn_add);


    }

    /**
     * This method is used to set listeners
     */
    @Override
    protected void initListeners() {
 //       mDrawerLayout.setDrawerListener(mDrawerToggle);
        imageButtonAdd.setOnClickListener(this);

   //     listView.setOnItemLongClickListener(this);
        imgAdd.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.company, menu);
        return true;
    }


    @Override
    protected void loadPageContentFromWeb() {
        super.loadPageContentFromWeb();
       // asyncLoadWebContent= new AsyncLoadWebContent();
       // asyncLoadWebContent.execute(URL);

    }

    @Override
    protected void refreshPageContent() {
        super.refreshPageContent();
    }


    @Override
    public void onClick(View view) {
        if(!hasNetwork()){
            String message=getCheckNetworkString();
            showAlertMessage(message);
            return;
        }
        switch(view.getId()){
            case R.id.img_add: openOrCloseForm();
                break;
            case R.id.btn_add: postForm();
                break;
        }
    }

    private void postForm() {
        boolean error=false;
        fName=inpFName.getText().toString();
        jobTitle=inpJobTitle.getText().toString();
        if(fName.length()<1){
            inpFName.setError("Please enter first  name");
            error=true;
        }
        else
        inpFName.setError(null);

        if(jobTitle.length()<1){
            inpJobTitle.setError("Please enter job title");
            error=true;
        }
        else
            inpJobTitle.setError(null);
        if(!error){
            inpFName.setText("");
            inpJobTitle.setText("");
           AsyncAddNewContent asyncAddNewContent= new AsyncAddNewContent();
           asyncAddNewContent.execute(URL_ADD);
            InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(btnAdd.getWindowToken(),0);
        }
    }

    private void openOrCloseForm() {
        if(layForm.isShown()){
            layForm.setVisibility(View.GONE);
        }
        else
            layForm.setVisibility(View.VISIBLE);
    }







    class AsyncAddNewContent extends AsyncTask<String,Integer,String> {


        @Override
        protected String doInBackground(String... strings) {
            ArrayList<NameValuePair> postData= User.getLoggedData(thisApp);
            postData.add(new BasicNameValuePair(Candidate.FNAME_FIELD, fName));
            postData.add(new BasicNameValuePair(Candidate.COMPANY_ID_FIELD, companyId+""));
            postData.add(new BasicNameValuePair(Candidate.JOB_TITLE_FIELD,jobTitle));
            String result="";
            try {
                UrlEncodedFormEntity entity= new UrlEncodedFormEntity(postData);
                LoadUrlData loadUrlData= new LoadUrlData(URL_ADD,entity);
                result=loadUrlData.getTargetData();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            return result;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }



        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            try {
                JSONObject jsonObject=new JSONObject(s);
                JSONObject model=jsonObject.getJSONObject(User.JSON_OBJECT_NAME);
                JSONArray content=model.getJSONArray("content");
                //Log.e("TREEEAY",content.toString());

                PostSuccessActionDialog confirm= new PostSuccessActionDialog();
                confirm.show(getSupportFragmentManager(),"TRAYTAG");


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }
}
