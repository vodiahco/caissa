package com.sharetray.caissa;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharetray.caissa.models.Candidate;
import com.sharetray.caissa.models.Company;

public class CandidateUpdateActivity extends ASActivity {

    TextView txtTitle,txtCompany,txtJobTitle;
    ImageView imgStatus;
    int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_update);

        initComponents();
        initListeners();

        Bundle bundle= getIntent().getExtras();
        String name=bundle.getString(Candidate.NAME_FIELD);
        String job=bundle.getString(Candidate.JOB_TITLE_FIELD);
        String company=bundle.getString(Company.TITLE_FIELD);
        status=bundle.getInt(Candidate.STATUS_FIELD);

        txtJobTitle.setText(job);
        txtCompany.setText(company);
        txtTitle.setText(name);
        setImageStatusResouce();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initComponents() {
        super.initComponents();
        txtTitle= (TextView) findViewById(R.id.txt_title);
        txtCompany= (TextView) findViewById(R.id.txt_company);
        txtJobTitle= (TextView) findViewById(R.id.txt_job_title);
        imgStatus= (ImageView) findViewById(R.id.img_status);
    }




    @Override
    protected void initListeners() {
        super.initListeners();
    }



    @Override
    protected void loadPageContentFromWeb() {
        super.loadPageContentFromWeb();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.candidate_update, menu);
        return true;
    }

    private void setImageStatusResouce(){
        switch (status){
            case 0: imgStatus.setImageResource(R.drawable.ic_action_av_stop_gray);
                break;
            case 1: imgStatus.setImageResource(R.drawable.ic_action_av_stop_lightgreen);
                break;
            case 2: imgStatus.setImageResource(R.drawable.ic_action_av_stop_green);
                break;
            case 3: imgStatus.setImageResource(R.drawable.ic_action_av_stop_red);
                break;
        }
    }


    
}
