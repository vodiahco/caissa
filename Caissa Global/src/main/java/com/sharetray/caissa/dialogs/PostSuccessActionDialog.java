package com.sharetray.caissa.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.sharetray.caissa.ASActivity;
import com.sharetray.caissa.MainActivity;
import com.sharetray.caissa.global.User;

/**
 * Created by Admin on 18/09/13.
 */
public class PostSuccessActionDialog extends DialogFragment {

    public PostSuccessActionDialog() {
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setMessage("Successfully added")
                .setPositiveButton("Add more", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences.Editor editor= User.getEditor(getActivity());
                        editor.putBoolean(ASActivity.NOTIFY_DATA_CHANGED_FIELD,true);
                        editor.commit();
                    }
                })
        .setNegativeButton("Back to list",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        return builder.create();
    }
}
