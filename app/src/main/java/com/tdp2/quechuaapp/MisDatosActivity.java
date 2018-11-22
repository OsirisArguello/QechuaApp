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
import android.widget.Toast;

import com.tdp2.quechuaapp.login.model.UserLogged;
import com.tdp2.quechuaapp.login.model.UserSessionManager;
import com.tdp2.quechuaapp.networking.Client;
import com.tdp2.quechuaapp.networking.LoginService;
import com.tdp2.quechuaapp.professor.InscripcionColoquioActivity;
import com.tdp2.quechuaapp.professor.NuevoColoquioActivity;
import com.tdp2.quechuaapp.utils.view.DialogBuilder;

public class MisDatosActivity extends AppCompatActivity {

    private UserSessionManager userSessionManager;
    private UserLogged userLogged;
    private LoginService loginService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_datos);
        userSessionManager = new UserSessionManager(this);
        userLogged=userSessionManager.getUserLogged();

        loginService=new LoginService();
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

        EditText nombreUsuarioEdit=findViewById(R.id.nombreUsuarioEdit);
        EditText apellidoUsuarioEdit=findViewById(R.id.apellidoUsuarioEdit);

        this.userLogged.firstName=nombreUsuarioEdit.getText().toString();
        this.userLogged.lastName=apellidoUsuarioEdit.getText().toString();

        //Invocar servicio
        loginService.updateUserLogged(this.userLogged, new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                ProgressBar loadingView = findViewById(R.id.loading_profesor_cursos_finales);
                loadingView.setVisibility(View.INVISIBLE);

                DialogBuilder.showAlert("Sus datos han sido actualizado correctamente", "Fecha de Final Asignada", MisDatosActivity.this, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                    }
                });
            }

            @Override
            public void onResponseError(String errorMessage) {
                ProgressBar loadingView = findViewById(R.id.loading_profesor_cursos_finales);
                loadingView.setVisibility(View.INVISIBLE);

                Toast.makeText(MisDatosActivity.this, "No fue posible conectarse al servidor, por favor reintente más tarde",
                        Toast.LENGTH_LONG).show();

                Thread thread = new Thread(){
                    @Override
                    public void run() {
                    try {
                        Thread.sleep(Toast.LENGTH_LONG); // As I am using LENGTH_LONG in Toast
                        Intent mainActivityIntent = new Intent(MisDatosActivity.this, MainActivity.class);
                        startActivity(mainActivityIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    }
                };
                thread.start();
            }

            @Override
            public Context getContext() {
                return MisDatosActivity.this;
            }
        });

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
