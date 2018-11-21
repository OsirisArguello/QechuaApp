package com.tdp2.quechuaapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tdp2.quechuaapp.login.model.UserLogged;
import com.tdp2.quechuaapp.login.model.UserSessionManager;

public class MisDatosActivity extends AppCompatActivity {

    private UserSessionManager userSessionManager;
    private UserLogged userLogged;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_datos);
        userSessionManager = new UserSessionManager(this);
        userLogged=userSessionManager.getUserLogged();


        setupUI();
        attachEvents();
    }

    private void attachEvents() {
        Button actualizarDatosButton=findViewById(R.id.actualizarDatosButton);
        actualizarDatosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarMensajeConfirmacion();
            }
        });
    }

    private void setupUI() {
        EditText nombreUsuarioEdit=findViewById(R.id.nombreUsuarioEdit);
        EditText apellidoUsuarioEdit=findViewById(R.id.apellidoUsuarioEdit);
        TextView mailUsuarioEdit=findViewById(R.id.mailUsuarioEdit);

        nombreUsuarioEdit.setHint(userLogged.firstName);
        apellidoUsuarioEdit.setHint(userLogged.lastName);
        mailUsuarioEdit.setText(userLogged.email);

    }

    private void mostrarMensajeConfirmacion() {

        String messageToDisplay="¿Está seguro que desea actualizar sus datos?";
        showConfirmationAlert(MisDatosActivity.this, "Confirmación de Actualización de Datos", messageToDisplay, "Aceptar","Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        modificarDatos();
                    }
                });
    }

    private void modificarDatos() {
        ProgressBar loadingView = findViewById(R.id.loading_mis_datos);
        loadingView.bringToFront();
        loadingView.setVisibility(View.VISIBLE);

        //Invocar servicio
    }

    public void showConfirmationAlert(Context context, String title, String messageToDisplay, String positiveMessage, String negativeMessage, DialogInterface.OnClickListener positiveListener){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(messageToDisplay);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, positiveMessage, positiveListener);


        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, negativeMessage,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        String messageToDisplay="¿Desea descartar los cambios?";
        showConfirmationAlert(MisDatosActivity.this, "Confirmación de Actualización de Datos", messageToDisplay, "Continuar","Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Intent mainIntent = new Intent(MisDatosActivity.this,MainActivity.class);
                        //startActivity(mainIntent);
                        goBack();
                    }
                });
    }

    public void goBack(){
        super.onBackPressed();
    }
}
