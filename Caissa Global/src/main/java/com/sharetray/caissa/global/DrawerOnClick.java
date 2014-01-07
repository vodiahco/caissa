package com.sharetray.caissa.global;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sharetray.caissa.LoginOptionsActivity;


/**
 * Created by Admin on 31/08/13.
 */
public class DrawerOnClick implements AdapterView.OnItemClickListener {

    Context context;
    DrawerLayout layout;
    ListView listView;
    public DrawerOnClick(Context context,DrawerLayout layout,ListView listView) {
        this.context=context;
        this.layout=layout;
        this.listView=listView;
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p/>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item=(String)parent.getItemAtPosition(position);
        Intent intent;



        if(item.equalsIgnoreCase("login")){
            intent= new Intent(context,LoginOptionsActivity.class);

            context.startActivity(intent);

        }

        layout.closeDrawer(listView);
    }
}
