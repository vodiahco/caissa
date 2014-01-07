package com.sharetray.caissa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sharetray.caissa.global.User;
import com.sharetray.caissa.models.LoginForm;
import com.sharetray.caissa.net.LoadUrlData;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Admin on 23/07/13.
 */
public class LoginOptionsActivity extends ASActivity implements View.OnClickListener {

    protected EditText email,pass;
    protected Button loginButton;
    protected TextView signUp,loginHelp,errorText;
    protected LoginForm loginFormModel;
    protected String emailVal;
    protected String passVal;
    protected String loginTime;

    protected static String LOGIN_URL=WEBSITE+"/mobile/indexjson/login";
    ArrayList<NameValuePair> data;
    UrlEncodedFormEntity encodedData;
    JSONArray jsonArray;
    JSONObject jsonObject;

    Context context;

    JSONObject model=null;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_loginoptions);
        initComponents();
        context=getApplicationContext();
        //PreferenceManager.setDefaultValues(this, R.xml.settings, false);
    }

    protected void initComponents(){
        email= (EditText) findViewById(R.id.inp_email);
        pass= (EditText) findViewById(R.id.inp_pass);
        loginButton= (Button) findViewById(R.id.btn_login);
//        signUp= (TextView) findViewById(R.id.txt_signup);
//        loginHelp= (TextView) findViewById(R.id.txt_help);
        errorText= (TextView) findViewById(R.id.txt_error);

        loginButton.setOnClickListener(this);
//        signUp.setOnClickListener(this);
//        loginHelp.setOnClickListener(this);
        email.setError("your account email");
        pass.setError("your account password");


    }

    /**
     * This method is used to set listeners
     */
    @Override
    protected void initListeners() {

    }




    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
       // finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        if(!hasNetwork()){
            String message=getCheckNetworkString();
            showAlertMessage(message);
            return;
        }
        switch (view.getId()){
            case R.id.btn_login: actionLogin();
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.login,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void actionSignup() {


    }

    private void showFormErrorMessage() {
        String em=loginFormModel.getErrorMessage();
        Toast toast= Toast.makeText(this, em, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL,0,0);
                toast.show();
        errorText.setText(em);
    }



    private void actionLogin() {

        emailVal=email.getText().toString();
        passVal=pass.getText().toString();

        Long timeNow= System.currentTimeMillis();
        loginTime = timeNow.toString();


        loginFormModel= new LoginForm(emailVal,passVal);
        if(!loginFormModel.validate())
            showFormErrorMessage();
    else{
        try{
           data= new ArrayList<NameValuePair>();
            data.add(new BasicNameValuePair(User.USERNAME_FIELD,emailVal));
            data.add(new BasicNameValuePair(User.PASSWORD_FIELD,passVal));
            data.add(new BasicNameValuePair(User.LOGIN_TIME_FIELD, loginTime));
            data.add(new BasicNameValuePair(User.OS_NAME_FIELD,User.OS_NAME));
            if(data !=null){
            encodedData= new UrlEncodedFormEntity(data);
                LoadUrlDataExec loadForm= new LoadUrlDataExec();
                loadForm.execute(LOGIN_URL);

                errorText.setText(getResources().getString(R.string.connecting));
            }
            else
            {
                Toast toast= Toast.makeText(getBaseContext(), getResources().getString(R.string.error_occurred), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_HORIZONTAL,10,10);
                toast.show();
            }

    }
        catch (UnsupportedEncodingException e){
            Toast toast= Toast.makeText(getBaseContext(), getResources().getString(R.string.error_occurred), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_HORIZONTAL,10,10);
            toast.show();
        }
        catch (Exception e){
            Toast toast= Toast.makeText(getBaseContext(), getResources().getString(R.string.error_occurred), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_HORIZONTAL,10,10);
            toast.show();
        }



        }
    }


    protected void handleWebLoad(JSONObject jsonObj) throws JSONException {
        if(jsonObj instanceof JSONObject){
            if(jsonObj.has(User.JSON_OBJECT_NAME)){
                model=jsonObj.getJSONObject(User.JSON_OBJECT_NAME);
                int resultCode=(model.has(User.RESULT_CODE))? model.getInt(User.RESULT_CODE):-1;
                if(resultCode==0){
                    login();
                    Intent intent= new Intent(this,MainActivity.class);
                    startActivity(intent);
                }
            }
        }
    }

    private void login() throws JSONException {
        SharedPreferences.Editor user= User.getEditor(this);
        if(model.has(User.LOGIN_TIME_FIELD))
            user.putString(User.LOGIN_TIME_FIELD,model.getString(User.LOGIN_TIME_FIELD));
        if(model.has(User.SESSION_ID_FIELD))
            user.putString(User.SESSION_ID_FIELD,model.getString(User.SESSION_ID_FIELD));
        if(model.has(User.SESSION_REQUEST_FIELD))
            user.putString(User.SESSION_REQUEST_FIELD,model.getString(User.SESSION_REQUEST_FIELD));
        if(model.has(User.LOGIN_CODE_FIELD))
            user.putString(User.LOGIN_CODE_FIELD,model.getString(User.LOGIN_CODE_FIELD));
        if(model.has(User.UID_FIELD))
            user.putInt(User.UID_FIELD,model.getInt(User.UID_FIELD));
        user.commit();

    }


    //inner class

    class LoadUrlDataExec extends AsyncTask<String,Integer,String> {


        @Override
        protected String doInBackground(String... strings) {
            JSONArray jArray=null;


            LoadUrlData loadUrlData= new LoadUrlData(LOGIN_URL,encodedData);
            String result=loadUrlData.getTargetData();

            return result;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("TRAYxxx",result);
            JSONObject jsonObj=null;
            String res;
            int resultCode;

            try{
                errorText.setTextColor(Color.BLUE);
                errorText.setText(getResources().getString(R.string.done));
                jsonObj= new JSONObject(result);
                model=jsonObj.getJSONObject(User.JSON_OBJECT_NAME);


//                res=model.getString(User.RESULT);
//                resultCode=model.getInt(User.RESULT_CODE);

                handleWebLoad(jsonObj);



            }catch (JSONException e) {

                Toast toast= Toast.makeText(getBaseContext(), getResources().getString(R.string.error_occurred), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_HORIZONTAL,10,10);
                toast.show();
            }
            catch (Exception e) {
                Toast toast= Toast.makeText(getBaseContext(), getResources().getString(R.string.error_occurred), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_HORIZONTAL,10,10);
                toast.show();
            }

        }





        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }



}
