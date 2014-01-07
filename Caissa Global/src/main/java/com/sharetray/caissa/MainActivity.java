package com.sharetray.caissa;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

//import com.fortysevendeg.android.swipelistview.SwipeListView;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.fortysevendeg.swipelistview.SwipeListViewListener;
import com.sharetray.caissa.adapter.CompanyExtraAdapter;
import com.sharetray.caissa.global.DrawerToggle;
import com.sharetray.caissa.global.EOnItemClickListener;
import com.sharetray.caissa.global.ESwipeListView;
import com.sharetray.caissa.global.User;
import com.sharetray.caissa.models.Candidate;
import com.sharetray.caissa.models.Company;
import com.sharetray.caissa.models.CompanyExtra;
import com.sharetray.caissa.net.LoadUrlData;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
//import com.nineoldandroids.animation.Animator;#


public class MainActivity extends ASActivity implements View.OnClickListener, EOnItemClickListener,CompanyExtraAdapter.BackViewButtonListener {
    protected DrawerLayout mDrawerLayout;
    protected DrawerToggle mDrawerToggle;
    protected ESwipeListView listView;
    protected ArrayList<CompanyExtra> companyExtras;
    CompanyExtraAdapter companyExtraAdapter;
    AsyncLoadWebContent asyncLoadWebContent;
    AsyncAddNewContent asyncAddNewContent;
    AsyncRemoveContent asyncRemoveContent;
    AsyncUpdateContent asyncUpdateContent;
    MainActivity thisApp;
    String title;
    EditText inpTitle;
    ImageButton imageButtonAdd;

    SwipeListView swipeListView;

    int listPosition,parentId,status;
    View listChildViewAtPosition;



    //Animator animator;



    private static final String URL=WEBSITE+"/mobile/company/index/extra/1";

    private static final String URL_ADD=WEBSITE+"/mobile/company/create";
    private String urlRem=WEBSITE+"/mobile/company/cancel";
    private String urlRem2=WEBSITE+"/mobile/company/cancelcandidate";
    private String urlUpdate=WEBSITE+"/mobile/company/updatecandidate";











    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setSupportProgressBarIndeterminateVisibility(true);

        setContentView(R.layout.activity_main);

        initComponents();
        initListeners();


        thisApp=this;

//        listView.setSwipeListViewListener(new SwipeListViewListener() {
//
//
//
//
//
//
//
//
//        });



       // mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setHomeButtonEnabled(true);




        // Set the drawer toggle as the DrawerListener


        loadPageContent();

    }

    /**
     * This method is used to initialize components
     */
    @Override
    protected void initComponents() {
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_main);
//        mDrawerToggle = new DrawerToggle(this, mDrawerLayout,
//                R.drawable.ic_drawer, R.string.app_name, R.string.app_name);
        companyExtras = new ArrayList<CompanyExtra>();
        listView = (ESwipeListView) findViewById(android.R.id.list);
        inpTitle= (EditText) findViewById(R.id.inp_title);
        imageButtonAdd= (ImageButton) findViewById(R.id.btn_add);

    }

    /**
     * This method is used to set listeners
     */
    @Override
    protected void initListeners() {
       // mDrawerLayout.setDrawerListener(mDrawerToggle);
        imageButtonAdd.setOnClickListener(this);
        listView.setOnSwipeItemClickListener(this);
       // listView.setOnItemClickListener(this);
 //       listView.setOnItemLongClickListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (mDrawerToggle.onOptionsItemSelected(item)) {
//
//            return true;
//        }


        return super.onOptionsItemSelected(item);

    }



    /**
     * This method is used to load page content from web or from local storage if available
     */
    @Override
    protected void loadPageContent(){
        if(!hasNetwork())
            loadPageContentFromLocal();
        else
            loadPageContentFromWeb();
    }



    /**
     * This method is used to load content from local server
     */
    @Override
    protected void loadPageContentFromLocal(){
        try {
            CompanyExtra companyExtraPref = new CompanyExtra(thisApp);
            String s=companyExtraPref.getPref();
            setSupportProgressBarIndeterminateVisibility(false);

            if(s==null)
                return;

            JSONObject jsonObject=new JSONObject(s);
            JSONObject model=jsonObject.getJSONObject(User.JSON_OBJECT_NAME);
            JSONArray content=model.getJSONArray("content");
            Log.e("TREEEAY",content.toString());

            for(int i=0;i<content.length();i++){
                JSONObject row=content.getJSONObject(i);
                CompanyExtra companyExtra= new CompanyExtra();
                companyExtra.setTitle(row.getString(Company.TITLE_FIELD));
                companyExtra.setParent(0);
                companyExtra.setPid(row.getInt(Company.PID_FIELD));
                //companyExtra.setStatus(row.getInt(Candidate.STATUS_FIELD));

                // companyExtra.setLastModified(row.getString(Company.LAST_MODIFIED_FIELD));
                // company.setPid(row.getInt(Company.PID_FIELD));
                companyExtras.add(companyExtra);

                JSONArray innerContent=row.getJSONArray("content");
                Log.e("TREEEAY",innerContent.toString());

                for(int j=0;j<innerContent.length();j++){
                    JSONObject rowInner=innerContent.getJSONObject(j);
                    CompanyExtra companyExtraInner= new CompanyExtra();
                    companyExtraInner.setTitle(rowInner.getString(Candidate.NAME_FIELD));
                    companyExtraInner.setJobTitle(rowInner.getString(Candidate.JOB_TITLE_FIELD));
                    int parentId=companyExtra.getPid();
                    companyExtraInner.setParent(parentId);
                    companyExtraInner.setPid(rowInner.getInt(Candidate.PID_FIELD));
                    companyExtraInner.setStatus(rowInner.getInt(Candidate.STATUS_FIELD));

                    // companyExtra.setLastModified(row.getString(Company.LAST_MODIFIED_FIELD));
                    // company.setPid(row.getInt(Company.PID_FIELD));
                    companyExtras.add(companyExtraInner);


                }
            }

            companyExtraAdapter = new CompanyExtraAdapter(thisApp, R.layout.companies, companyExtras);
            listView.setAdapter(companyExtraAdapter);

            SharedPreferences.Editor editor= User.getEditor(thisApp);
            editor.putBoolean(ASActivity.NOTIFY_DATA_CHANGED_FIELD,false);
            editor.commit();

        } catch (JSONException e) {
            e.printStackTrace();
        }

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
        companyExtras.clear();
        asyncLoadWebContent= new AsyncLoadWebContent();
        asyncLoadWebContent.execute(URL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(asyncLoadWebContent !=null)
        asyncLoadWebContent.cancel(true);


    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref= User.getSharedPreferences(this);
        boolean changed=pref.getBoolean(NOTIFY_DATA_CHANGED_FIELD,false);
        if(changed){
            refreshPageContent();
        }


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
    //    mDrawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
       // mDrawerToggle.onConfigurationChanged(newConfig);
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
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.removeItem(R.id.action_list);

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public void onClick(View view) {
        if(!hasNetwork()){
            String message=getCheckNetworkString();
            showAlertMessage(message);
            return;
        }

        title= inpTitle.getText().toString();
        if(title.length()>0){
            inpTitle.setText("");
        asyncAddNewContent= new AsyncAddNewContent();
        asyncAddNewContent.execute(URL_ADD);
            InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(imageButtonAdd.getWindowToken(),0);
        }
        else{
            inpTitle.setError("Please enter company name");
        }
    }



    @Override
    public void onSwipeItemClicked(int i) {
        if(!hasNetwork()){
String message=getCheckNetworkString();
            showAlertMessage(message);
            return;
        }

        Log.e("TRAYZZZ",i+"---pos");
        CompanyExtra companyExtra= companyExtras.get(i);
        if(companyExtra.getParent()==0){
        String title=companyExtra.getTitle();
        int pid=companyExtra.getPid();
        Bundle bundle= new Bundle();
        bundle.putString(Company.TITLE_FIELD,title);
        bundle.putInt(Company.PID_FIELD,pid);
        Intent intent= new Intent(this,CompanyActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        }


    }

    @Override
    public void onBackViewChildClicked(View view,int position) {
        if(!hasNetwork()){
            String message=getCheckNetworkString();
            showAlertMessage(message);
            return;
        }

       switch(view.getId()){

           case R.id.btn_remove: Log.e("TARY",position+" child id"+view.getId());
               CompanyExtra companyExtra= companyExtras.get(position);
               int pid=companyExtra.getPid();
               this.parentId=companyExtra.getParent();
               listPosition=companyExtra.getPid();
              // Log.e("TARYPARENT",parentId+" parent id"+view.getId());

               listChildViewAtPosition=view;
               DialogFragment deleteConfirm= new DeleteConfirmation(view,pid);
               deleteConfirm.show(getSupportFragmentManager(),"TRAT");
               break;
           case R.id.btn_view: Log.e("TARY",position+" child id"+view.getId());
               companyExtra= companyExtras.get(position);
               pid=companyExtra.getPid();
               this.parentId=companyExtra.getParent();
               listPosition=companyExtra.getPid();
              // Log.e("TARYPARENT",parentId+" parent id"+view.getId());
               if(companyExtra.getParent()==0){
                   String title=companyExtra.getTitle();
                   Bundle bundle= new Bundle();
                   bundle.putString(Company.TITLE_FIELD,title);
                   bundle.putInt(Company.PID_FIELD,pid);
                   Intent intent= new Intent(this,CompanyActivity.class);
                   intent.putExtras(bundle);
                   startActivity(intent);
               }
               else if(companyExtra.getParent()>0){
                   //Log.e("TARYPARENT",parentId+" parent id"+view.getId());
                   UpdateConfirmation updateConfirmation= new UpdateConfirmation(view,pid);
                   updateConfirmation.show(getSupportFragmentManager(),"TRAT");

               }
               break;
       }



    }


    class AsyncLoadWebContent extends AsyncTask<String,Integer,String> {


        @Override
        protected String doInBackground(String... strings) {
            if(!hasNetwork()){

                return null;
            }
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
                CompanyExtra companyExtraPref= new CompanyExtra(thisApp);
                companyExtraPref.setPref(s);
                setSupportProgressBarIndeterminateVisibility(false);

                JSONObject jsonObject=new JSONObject(s);
                JSONObject model=jsonObject.getJSONObject(User.JSON_OBJECT_NAME);
                JSONArray content=model.getJSONArray("content");
                Log.e("TREEEAY",content.toString());

                for(int i=0;i<content.length();i++){
                    JSONObject row=content.getJSONObject(i);
                   CompanyExtra companyExtra= new CompanyExtra();
                    companyExtra.setTitle(row.getString(Company.TITLE_FIELD));
                    companyExtra.setParent(0);
                    companyExtra.setPid(row.getInt(Company.PID_FIELD));
                    //companyExtra.setStatus(row.getInt(Candidate.STATUS_FIELD));

                   // companyExtra.setLastModified(row.getString(Company.LAST_MODIFIED_FIELD));
                   // company.setPid(row.getInt(Company.PID_FIELD));
                    companyExtras.add(companyExtra);

                    JSONArray innerContent=row.getJSONArray("content");
                   // Log.e("TREEEAY",innerContent.toString());

                    for(int j=0;j<innerContent.length();j++){
                        JSONObject rowInner=innerContent.getJSONObject(j);
                        CompanyExtra companyExtraInner= new CompanyExtra();
                        companyExtraInner.setTitle(rowInner.getString(Candidate.NAME_FIELD));
                        companyExtraInner.setJobTitle(rowInner.getString(Candidate.JOB_TITLE_FIELD));
                        int parentId=companyExtra.getPid();
                        companyExtraInner.setParent(parentId);
                        companyExtraInner.setPid(rowInner.getInt(Candidate.PID_FIELD));
                        companyExtraInner.setStatus(rowInner.getInt(Candidate.STATUS_FIELD));

                        // companyExtra.setLastModified(row.getString(Company.LAST_MODIFIED_FIELD));
                        // company.setPid(row.getInt(Company.PID_FIELD));
                        companyExtras.add(companyExtraInner);


                    }
                }

                companyExtraAdapter = new CompanyExtraAdapter(thisApp, R.layout.companies, companyExtras);
                listView.setAdapter(companyExtraAdapter);

                SharedPreferences.Editor editor= User.getEditor(thisApp);
                editor.putBoolean(ASActivity.NOTIFY_DATA_CHANGED_FIELD,false);
                editor.commit();

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
                CompanyExtra companyExtraPref= new CompanyExtra(thisApp);
                companyExtraPref.setPref(s);

                JSONObject jsonObject=new JSONObject(s);
                JSONObject model=jsonObject.getJSONObject(User.JSON_OBJECT_NAME);
                JSONArray content=model.getJSONArray("content");
               // Log.e("TREEEAY",content.toString());
                companyExtras.clear();
                for(int i=0;i<content.length();i++){
                    JSONObject row=content.getJSONObject(i);
                    CompanyExtra companyExtra= new CompanyExtra();
                    companyExtra.setTitle(row.getString(Company.TITLE_FIELD));
                    companyExtra.setParent(0);
                    companyExtra.setPid(row.getInt(Company.PID_FIELD));
                    //companyExtra.setStatus(row.getInt(Candidate.STATUS_FIELD));

                    // companyExtra.setLastModified(row.getString(Company.LAST_MODIFIED_FIELD));
                    // company.setPid(row.getInt(Company.PID_FIELD));
                    companyExtras.add(companyExtra);

                    JSONArray innerContent=row.getJSONArray("content");
                    //Log.e("TREEEAY",innerContent.toString());

                    for(int j=0;j<innerContent.length();j++){
                        JSONObject rowInner=innerContent.getJSONObject(j);
                        CompanyExtra companyExtraInner= new CompanyExtra();
                        companyExtraInner.setTitle(rowInner.getString(Candidate.NAME_FIELD));
                        companyExtraInner.setJobTitle(rowInner.getString(Candidate.JOB_TITLE_FIELD));
                        int parentId=companyExtra.getPid();
                        companyExtraInner.setParent(parentId);
                        companyExtraInner.setPid(rowInner.getInt(Candidate.PID_FIELD));
                        companyExtraInner.setStatus(rowInner.getInt(Candidate.STATUS_FIELD));

                        // companyExtra.setLastModified(row.getString(Company.LAST_MODIFIED_FIELD));
                        // company.setPid(row.getInt(Company.PID_FIELD));
                        companyExtras.add(companyExtraInner);


                    }
                }

               // candidateAdapter= new CompanyAdapter(thisApp,R.layout.companyExtras,companyExtras);
               // listView.setAdapter(candidateAdapter);
                companyExtraAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


}





    class AsyncRemoveContent extends AsyncTask<String,Integer,String> {


        @Override
        protected String doInBackground(String... strings) {
            ArrayList<NameValuePair> postData= User.getLoggedData(thisApp);
            postData.add(new BasicNameValuePair(Company.PID_FIELD,strings[0]));
            String result="";
            try {
                UrlEncodedFormEntity entity= new UrlEncodedFormEntity(postData);
                String url=urlRem;
                if(parentId>0)
                    url=urlRem2;
                LoadUrlData loadUrlData= new LoadUrlData(url,entity);
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
                CompanyExtra companyExtraPref= new CompanyExtra(thisApp);
                companyExtraPref.setPref(s);

                JSONObject jsonObject=new JSONObject(s);
                JSONObject model=jsonObject.getJSONObject(User.JSON_OBJECT_NAME);
                JSONArray content=model.getJSONArray("content");
               // Log.e("TREEEAY",content.toString());
                companyExtras.clear();
                for(int i=0;i<content.length();i++){
                    JSONObject row=content.getJSONObject(i);
                    CompanyExtra companyExtra= new CompanyExtra();
                    companyExtra.setTitle(row.getString(Company.TITLE_FIELD));
                    companyExtra.setParent(0);
                    companyExtra.setPid(row.getInt(Company.PID_FIELD));
                    //companyExtra.setStatus(row.getInt(Candidate.STATUS_FIELD));

                    // companyExtra.setLastModified(row.getString(Company.LAST_MODIFIED_FIELD));
                    // company.setPid(row.getInt(Company.PID_FIELD));
                    companyExtras.add(companyExtra);

                    JSONArray innerContent=row.getJSONArray("content");
                    //Log.e("TREEEAY",innerContent.toString());

                    for(int j=0;j<innerContent.length();j++){
                        JSONObject rowInner=innerContent.getJSONObject(j);
                        CompanyExtra companyExtraInner= new CompanyExtra();
                        companyExtraInner.setTitle(rowInner.getString(Candidate.NAME_FIELD));
                        companyExtraInner.setJobTitle(rowInner.getString(Candidate.JOB_TITLE_FIELD));
                        int parentId=companyExtra.getPid();
                        companyExtraInner.setParent(parentId);
                        companyExtraInner.setPid(rowInner.getInt(Candidate.PID_FIELD));
                        companyExtraInner.setStatus(rowInner.getInt(Candidate.STATUS_FIELD));

                        // companyExtra.setLastModified(row.getString(Company.LAST_MODIFIED_FIELD));
                        // company.setPid(row.getInt(Company.PID_FIELD));
                        companyExtras.add(companyExtraInner);


                    }
                }

                // candidateAdapter= new CompanyAdapter(thisApp,R.layout.companyExtras,companyExtras);
                // listView.setAdapter(candidateAdapter);
                companyExtraAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }







    class AsyncUpdateContent extends AsyncTask<String,Integer,String> {


        @Override
        protected String doInBackground(String... strings) {
            ArrayList<NameValuePair> postData= User.getLoggedData(thisApp);
            postData.add(new BasicNameValuePair(Company.PID_FIELD,strings[0]));
            postData.add(new BasicNameValuePair(Company.STATUS_FIELD,status+""));
            String result="";
            try {
                UrlEncodedFormEntity entity= new UrlEncodedFormEntity(postData);
                String url=urlUpdate;

                LoadUrlData loadUrlData= new LoadUrlData(url,entity);
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
                CompanyExtra companyExtraPref= new CompanyExtra(thisApp);
                companyExtraPref.setPref(s);

                JSONObject jsonObject=new JSONObject(s);
                JSONObject model=jsonObject.getJSONObject(User.JSON_OBJECT_NAME);
                JSONArray content=model.getJSONArray("content");
            //    Log.e("TREEEAY",content.toString());
                companyExtras.clear();
                for(int i=0;i<content.length();i++){
                    JSONObject row=content.getJSONObject(i);
                    CompanyExtra companyExtra= new CompanyExtra();
                    companyExtra.setTitle(row.getString(Company.TITLE_FIELD));
                    companyExtra.setParent(0);
                    companyExtra.setPid(row.getInt(Company.PID_FIELD));
                    //companyExtra.setStatus(row.getInt(Candidate.STATUS_FIELD));

                    // companyExtra.setLastModified(row.getString(Company.LAST_MODIFIED_FIELD));
                    // company.setPid(row.getInt(Company.PID_FIELD));
                    companyExtras.add(companyExtra);

                    JSONArray innerContent=row.getJSONArray("content");
                   // Log.e("TREEEAY",innerContent.toString());

                    for(int j=0;j<innerContent.length();j++){
                        JSONObject rowInner=innerContent.getJSONObject(j);
                        CompanyExtra companyExtraInner= new CompanyExtra();
                        companyExtraInner.setTitle(rowInner.getString(Candidate.NAME_FIELD));
                        companyExtraInner.setJobTitle(rowInner.getString(Candidate.JOB_TITLE_FIELD));
                        int parentId=companyExtra.getPid();
                        companyExtraInner.setParent(parentId);
                        companyExtraInner.setPid(rowInner.getInt(Candidate.PID_FIELD));
                        companyExtraInner.setStatus(rowInner.getInt(Candidate.STATUS_FIELD));

                        // companyExtra.setLastModified(row.getString(Company.LAST_MODIFIED_FIELD));
                        // company.setPid(row.getInt(Company.PID_FIELD));
                        companyExtras.add(companyExtraInner);


                    }
                }

                // candidateAdapter= new CompanyAdapter(thisApp,R.layout.companyExtras,companyExtras);
                // listView.setAdapter(candidateAdapter);
                companyExtraAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }





    class DeleteConfirmation extends DialogFragment{

        int pid,parentId;
        View view;

        public DeleteConfirmation(View view,int id) {

            super();
            pid=id;
            this.view=view;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder= new AlertDialog.Builder(thisApp);
            builder.setMessage("Do you want to delete this row?")
                    .setPositiveButton("Delete",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        //    Log.e("TRATDSIAL",pid+" delete row?");
                            asyncRemoveContent= new AsyncRemoveContent();
                            asyncRemoveContent.execute(pid+"");
                        }
                    })
                    .setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        //    Log.e("TRATDSIAL",pid+" cancel delete row?");
                        }
                    });
            return builder.create();
        }
    }




    class UpdateConfirmation extends DialogFragment{

        int pid,parentId;
        View view;

        public UpdateConfirmation(View view,int id) {
            super();
            pid=id;
            this.view=view;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder= new AlertDialog.Builder(thisApp);
            builder.setTitle("Update candidate status")
                    .setItems(R.array.status_array, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            status=i;
                          //  Log.e("TRATDSIAL", status + " update?");
                            asyncUpdateContent = new AsyncUpdateContent();
                            asyncUpdateContent.execute(pid+"");
                        }
                    })

                    .setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                          //  Log.e("TRATDSIAL", pid + " cancel delete row?");
                        }
                    });
            return builder.create();
        }
    }








}
