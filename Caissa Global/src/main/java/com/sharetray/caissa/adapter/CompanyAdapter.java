package com.sharetray.caissa.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sharetray.caissa.R;
import com.sharetray.caissa.models.Company;

import java.util.ArrayList;

/**
 * Created by Admin on 08/09/13.
 */


public class CompanyAdapter extends ArrayAdapter {

    protected ArrayList<Company> companies;
    protected Activity context;


    public CompanyAdapter(Activity context, int resource, ArrayList<Company> objects) {
        super(context, resource, objects);
        companies=objects;
        this.context=context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.companies,parent,false);
        TextView textView= (TextView) row.findViewById(R.id.lay_title);
        Company company= companies.get(position);
        String title= company.getTitle();
        textView.setText(title);


        return row;
    }
}
