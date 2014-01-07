package com.sharetray.caissa.global;

import android.app.Activity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.sharetray.caissa.R;

/**
 * Created by Admin on 31/08/13.
 */
public class DrawerToggleLogin extends ActionBarDrawerToggle {

    ActionBarActivity activity;

    private String[] mPlanetTitles;
    protected ListView mDrawerList;
    protected DrawerLayout mDrawerLayout;

    protected boolean listViewReloadingRequired =true;

    public DrawerToggleLogin(Activity activity, DrawerLayout drawerLayout, int drawerImageRes, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes, closeDrawerContentDescRes);
        this.activity=(ActionBarActivity)activity;
        this.mDrawerLayout=drawerLayout;

        mPlanetTitles = activity.getResources().getStringArray(R.array.login_array);


        mDrawerList = (ListView) activity.findViewById(R.id.nav_drawer);
        mDrawerList.setOnItemClickListener(new DrawerOnClick(activity,drawerLayout,mDrawerList));

        // Set the adapter for the list view
        mDrawerList.setAdapter(new DrawerListAdapter(activity, R.layout.drawer_list_item, mPlanetTitles));
        Log.e("TAGG","items loaded");
    }


    /** Called when a drawer has settled in a completely closed state. */
    public void onDrawerClosed(View view) {
        listViewReloadingRequired =true;
      //  activity.getSupportActionBar().setTitle("closed");

    }

    /** Called when a drawer has settled in a completely open state. */
    public void onDrawerOpened(View drawerView) {
      //  activity.getSupportActionBar().setTitle("openned");
        Log.e("TAGG","items reloaded");
    }


    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        super.onDrawerSlide(drawerView, slideOffset);
        if(listViewReloadingRequired){
        Log.e("TAGGG",slideOffset+"");
          listViewReloadingRequired=false;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
        if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            mDrawerLayout.openDrawer(mDrawerList);
        }
        }
        return super.onOptionsItemSelected(item);
    }

    public ListView getListView(){
        return this.mDrawerList;
    }
}


