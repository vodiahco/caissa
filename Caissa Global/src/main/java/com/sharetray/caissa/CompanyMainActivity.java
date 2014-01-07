package com.sharetray.caissa;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.sharetray.caissa.adapter.CompanyAdapter;
import com.sharetray.caissa.global.DrawerToggle;
import com.sharetray.caissa.global.User;
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

public class CompanyMainActivity extends ASActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    protected DrawerLayout mDrawerLayout;
    protected DrawerToggle mDrawerToggle;
    protected ListView listView;
    protected ArrayList<Company> companies;
    CompanyAdapter companyAdapter;
    AsyncLoadWebContent asyncLoadWebContent;
    AsyncAddNewContent asyncAddNewContent;
    CompanyMainActivity thisApp;
    String title;
    EditText inpTitle;
    ImageButton imageButtonAdd;


    private static final String URL=WEBSITE+"/mobile/company";

    private static final String URL_ADD=WEBSITE+"/mobile/company/create";









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);

        initComponents();
        initListeners();


        thisApp=this;


       // mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setHomeButtonEnabled(true);




        // Set the drawer toggle as the DrawerListener


        loadPageContentFromWeb();

    }

    /**
     * This method is used to initialize components
     */
    @Override
    protected void initComponents() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_main);
        mDrawerToggle = new DrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.app_name, R.string.app_name);
        companies= new ArrayList<Company>();
        listView = (ListView) findViewById(android.R.id.list);
        inpTitle= (EditText) findViewById(R.id.inp_title);
        imageButtonAdd= (ImageButton) findViewById(R.id.btn_add);

    }

    /**
     * This method is used to set listeners
     */
    @Override
    protected void initListeners() {
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        imageButtonAdd.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {

            return true;
        }


        return super.onOptionsItemSelected(item);

    }





    /**
     * This method is used to load content from web
     */
    @Override
    protected void loadPageContentFromWeb() {

        asyncLoadWebContent= new AsyncLoadWebContent();
       asyncLoadWebContent.execute(URL);


    }

    /**
     * This method is used to refresh list content
     */
    @Override
    protected void refreshPageContent() {
    }

    @Override
    protected void onPause() {
        super.onPause();
        asyncLoadWebContent.cancel(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        title= inpTitle.getText().toString();
        if(title.length()>0){
            inpTitle.setText("");
        asyncAddNewContent= new AsyncAddNewContent();
        asyncAddNewContent.execute(URL_ADD);
        }
        else{
            inpTitle.setError("Please enter company name");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ImageView img= (ImageView) view.findViewById(R.id.img_remove);
        if(img.isShown()){

        }
        else{
        Company company=companies.get(i);
        String title=company.getTitle();
        int pid=company.getPid();
        Bundle bundle= new Bundle();
        bundle.putString(Company.TITLE_FIELD,title);
        bundle.putInt(Company.PID_FIELD,pid);
        Intent intent= new Intent(this,CompanyActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        ImageView img= (ImageView) view.findViewById(R.id.img_remove);
        if (img.isShown()) {
            img.setVisibility(View.GONE);
        }
        else{
            img.setVisibility(View.VISIBLE);
        }
        return true;
    }


    class AsyncLoadWebContent extends AsyncTask<String,Integer,String> {


        @Override
        protected String doInBackground(String... strings) {
            ArrayList<NameValuePair> postData= User.getLoggedData(thisApp);
            String result="0";
            try {
                UrlEncodedFormEntity entity= new UrlEncodedFormEntity(postData);
                LoadUrlData loadUrlData= new LoadUrlData(URL,entity);
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
                Log.e("TREEEAY",content.toString());
                for(int i=0;i<content.length();i++){
                    JSONObject row=content.getJSONObject(i);
                   Company company= new Company();
                    company.setTitle(row.getString(Company.TITLE_FIELD));
                    company.setLastModified(row.getString(Company.LAST_MODIFIED_FIELD));
                   // company.setPid(row.getInt(Company.PID_FIELD));
                    companies.add(company);
                }

                companyAdapter= new CompanyAdapter(thisApp, R.layout.companies,companies);
                listView.setAdapter(companyAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }




    class AsyncAddNewContent extends AsyncTask<String,Integer,String> {


        @Override
        protected String doInBackground(String... strings) {
            ArrayList<NameValuePair> postData= User.getLoggedData(thisApp);
            postData.add(new BasicNameValuePair(Company.TITLE_FIELD,title));
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
                Log.e("TREEEAY",content.toString());
                companies.clear();
                for(int i=0;i<content.length();i++){
                    JSONObject row=content.getJSONObject(i);
                    Company company= new Company();
                    company.setTitle(row.getString(Company.TITLE_FIELD));
                    company.setLastModified(row.getString(Company.LAST_MODIFIED_FIELD));
                    // company.setPid(row.getInt(Company.PID_FIELD));
                    companies.add(company);
                }

               // candidateAdapter= new CompanyAdapter(thisApp,R.layout.companyExtras,companyExtras);
               // listView.setAdapter(candidateAdapter);
                companyAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


}


}
