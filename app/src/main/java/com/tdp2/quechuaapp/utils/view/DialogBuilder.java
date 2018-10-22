package com.tdp2.quechuaapp.utils.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;


public class DialogBuilder {

    public static void showAlert(String messageToDisplay, String title, Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(messageToDisplay);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", DialogBuilder.getDialogDismissListener());
        alertDialog.show();
    }

    public static void showConfirmationDialog(String messageToDisplay, String title, String respuestaPositiva, String respuestaNegativa, DialogInterface.OnClickListener respuestaPositivaOnClickListener, Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(messageToDisplay);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, respuestaPositiva, getDialogDismissListener());

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, respuestaNegativa,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static DialogInterface.OnClickListener getDialogDismissListener(){
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };
    }

}
