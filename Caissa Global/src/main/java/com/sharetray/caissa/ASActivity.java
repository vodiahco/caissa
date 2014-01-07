package com.sharetray.caissa;


import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.sharetray.caissa.global.AsyncNewPost;
import com.sharetray.caissa.global.DrawerToggle;


public abstract class ASActivity extends ActionBarActivity {


    protected Toast toast;

    protected final static String WEBSITE="http://caissa.co.uk/mobile/index.php";
    public static final String NOTIFY_DATA_CHANGED_FIELD="data_changed";


    /**
     * list content url
     */

    protected String listContentUrl;

    /**
     * item content url
     */
    protected String itemContentUrl;

    /**
     * post content url
     */
    protected String postContentUrl;

    protected DrawerLayout mDrawerLayout;
    protected DrawerToggle mDrawerToggle;

    protected void showAlertMessage(String message){
        if(toast!=null)
            toast.cancel();
        toast=Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL,10,10);
        toast.show();
    }

    protected String getCheckNetworkString(){
        String message=getResources().getString(R.string.check_network);
        return  message;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.action_list: openMain();
                break;
            case R.id.action_history: openHistory();
                break;

        }

      return super.onOptionsItemSelected(item);

    }

    protected void openMain() {
    Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);

    }

    protected void openHistory() {
        Intent intent=new Intent(this,HistoryActivity.class);
        startActivity(intent);

    }




    /**
     * This method is used to check if a user is logged in
     * @return boolean indicating login status
     */
    protected boolean isLogged(){
    return true;
}

    /**
     * This method is used to check if user has a valid GCM id
     * @return boolean
     */
    protected boolean hasValidPushId(){
        return true;
    }

    /**
     * This method is used to test if an activity has new server content that it should load
     * @return boolean
     */
    protected boolean hasNewPost(){
        return true;
    }


    /**
     *  this method is used to check for new content on the server, if new content available, it tries to load the content
     */
    protected void checkAndLoadNewPost(){
        AsyncNewPost asyncNewPost= new AsyncNewPost();
        asyncNewPost.execute();

    }

    /**
     * This method is used to register a user for GCM push message
     */
    protected void registerForPush(){

    }

    /**
     * This method is used to check device network connections, if network is not detected, it notifies the user
     */
    protected void checkNetwork(){

    }

    /**
     * This method is used to test device network connections, if network is not detected, it returns false
     */
    protected boolean hasNetwork(){
        ConnectivityManager conn= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo net=  conn.getActiveNetworkInfo();
       return (net!=null);
    }

    /**
     * This method is used to load page content from web or from local storage if available
     */
    protected void loadPageContent(){
        if(hasLocalPageContent())
            loadPageContentFromLocal();
        else
            loadPageContentFromWeb();
    }



    /**
     * This method is used to load content from local server
     */
    protected void loadPageContentFromLocal(){}


    /**
     * This method is used to load content from web
     */
    protected void loadPageContentFromWeb(){}


    /**
     *  This method is used to refresh list content
     */
    protected void refreshPageContent(){}


    /**
     * This method is used to test the availability of content on local storage
     * @return boolean
     */
    protected boolean hasLocalPageContent(){
        return false;
    }

    /**
     * This method is used to initialize components
     */
    protected void initComponents(){}

    /**
     * This method is used to set listeners
     */
    protected void initListeners(){}

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
    }
}
