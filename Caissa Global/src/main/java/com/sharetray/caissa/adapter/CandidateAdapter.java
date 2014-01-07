package com.sharetray.caissa.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharetray.caissa.R;
import com.sharetray.caissa.models.Candidate;

import java.util.ArrayList;

/**
 * Created by Admin on 08/09/13.
 */


public class CandidateAdapter extends ArrayAdapter {

    protected ArrayList<Candidate> candidates;
    protected Activity context;


    public CandidateAdapter(Activity context, int resource, ArrayList<Candidate> objects) {
        super(context, resource, objects);
        candidates=objects;
        this.context=context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.candidates,parent,false);
        TextView textViewName= (TextView) row.findViewById(R.id.lay_name);
        TextView textViewJobTitle= (TextView) row.findViewById(R.id.lay_job_title);
        ImageView statusImage=(ImageView) row.findViewById(R.id.img_status);
        Candidate candidate= candidates.get(position);
        String name= candidate.getName();
        String jobTitle= candidate.getJobTitle();
        int pid= candidate.getPid();
        int status= candidate.getStatus();

        textViewName.setText(name);
        textViewJobTitle.setText(jobTitle);

        switch (status){
            case 0: statusImage.setImageResource(R.drawable.ic_action_av_stop_gray);
                break;
            case 1: statusImage.setImageResource(R.drawable.ic_action_av_stop_lightgreen);
                break;
            case 2: statusImage.setImageResource(R.drawable.ic_action_av_stop_green);
                break;
            case 3: statusImage.setImageResource(R.drawable.ic_action_av_stop_red);
                break;
        }

        return row;
    }
}
