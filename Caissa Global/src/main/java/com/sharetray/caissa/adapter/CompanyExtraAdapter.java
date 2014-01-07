package com.sharetray.caissa.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sharetray.caissa.R;
import com.sharetray.caissa.models.CompanyExtra;

import java.util.ArrayList;

/**
 * Created by Admin on 08/09/13.
 */


public class CompanyExtraAdapter extends ArrayAdapter {

    protected ArrayList<CompanyExtra> items;
    protected Activity context;
    ImageView statusImage;
    BackViewButtonListener backViewButtonListener;


    public interface BackViewButtonListener{
        public void onBackViewChildClicked(View view,int position);
    }


    public CompanyExtraAdapter(Activity context, int resource, ArrayList<CompanyExtra> objects) {
        super(context, resource, objects);
        items=objects;
        this.context=context;
        try{
            backViewButtonListener=(BackViewButtonListener)context;
        }
        catch (Exception e){
            throw new ClassCastException(context.toString()
                    + " must implement BackViewButtonListener");

        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row=convertView;
        CompanyExtra model= items.get(position);
        TextView textView;
        final int positionx=position;


        if(model.getParent()!=0){
        LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row=inflater.inflate(R.layout.candidates,parent,false);
        LinearLayout frontRow= (LinearLayout) row.findViewById(R.id.front);

        textView= (TextView) row.findViewById(R.id.lay_name);


        TextView textViewJobTitle= (TextView) row.findViewById(R.id.lay_job_title);
            ImageButton removeButton= (ImageButton) row.findViewById(R.id.btn_remove);
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id=view.getId();
                    backViewButtonListener.onBackViewChildClicked(view,positionx);
                }
            });
            ImageButton editButton= (ImageButton) row.findViewById(R.id.btn_view);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id=view.getId();
                    backViewButtonListener.onBackViewChildClicked(view,positionx);
                }
            });


        String name= model.getTitle();
        String jobTitle= model.getJobTitle();
        int pid= model.getPid();
        int status= model.getStatus();

            textView.setText(name);
        textViewJobTitle.setText(jobTitle);

        switch (status){
            case 0: //statusImage.setImageResource(R.drawable.ic_action_av_stop_gray);
                frontRow.setBackgroundColor(context.getResources().getColor(R.color.MintCream));
                break;
            case 1: //statusImage.setImageResource(R.drawable.ic_action_av_stop_lightgreen);
                frontRow.setBackgroundColor(context.getResources().getColor(R.color.LightCyan));
                break;
            case 2: //statusImage.setImageResource(R.drawable.ic_action_av_stop_green);
                frontRow.setBackgroundColor(context.getResources().getColor(R.color.PaleGreen));
                break;
            case 3: //statusImage.setImageResource(R.drawable.ic_action_av_stop_red);
                frontRow.setBackgroundColor(context.getResources().getColor(R.color.DarkSeaGreen));
                break;
            case 4: //statusImage.setImageResource(R.drawable.ic_action_av_stop_red);
                frontRow.setBackgroundColor(context.getResources().getColor(R.color.NavajoWhite));
                break;
            case 5: //statusImage.setImageResource(R.drawable.ic_action_av_stop_red);
                frontRow.setBackgroundColor(context.getResources().getColor(R.color.Coral));
                break;
        }
        }
        else{
            LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(R.layout.companies,parent,false);
            textView= (TextView) row.findViewById(R.id.lay_title);
            String title= model.getTitle();
            textView.setText(title);
            ImageButton editButton= (ImageButton) row.findViewById(R.id.btn_view);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id=view.getId();
                    backViewButtonListener.onBackViewChildClicked(view,positionx);
                }
            });

            ImageButton removeButton= (ImageButton) row.findViewById(R.id.btn_remove);
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id=view.getId();
                    backViewButtonListener.onBackViewChildClicked(view,positionx);
                }
            });

        }



        return row;
    }
}
