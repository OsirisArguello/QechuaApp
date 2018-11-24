package com.tdp2.quechuaapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tdp2.quechuaapp.login.model.UserLogged;
import com.tdp2.quechuaapp.login.model.UserSessionManager;
import com.tdp2.quechuaapp.model.Alumno;
import com.tdp2.quechuaapp.networking.Client;
import com.tdp2.quechuaapp.networking.EstudianteService;
import com.tdp2.quechuaapp.networking.LoginService;
import com.tdp2.quechuaapp.professor.InscripcionColoquioActivity;
import com.tdp2.quechuaapp.professor.NuevoColoquioActivity;
import com.tdp2.quechuaapp.utils.view.DialogBuilder;

public class MisDatosActivity extends AppCompatActivity {

    private UserSessionManager userSessionManager;
    private UserLogged userLogged;
    private Alumno alumno;
    private EstudianteService estudianteService;
    private EditText nombreUsuarioEdit;
    private EditText apellidoUsuarioEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_datos);
        userSessionManager = new UserSessionManager(this);
        userLogged=userSessionManager.getUserLogged();
        alumno = (Alumno) getIntent().getSerializableExtra("alumno");

        estudianteService=new EstudianteService();
        setupUI();
        attachEvents();
    }

    private void attachEvents() {
        Button actualizarDatosButton=findViewById(R.id.actualizarDatosButton);
        actualizarDatosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarIngresos()){
                    mostrarMensajeConfirmacion();
                } else {
                    return;
                }
            }
        });
    }

    private boolean validarIngresos() {
        if (TextUtils.isEmpty(nombreUsuarioEdit.getText())) {
            nombreUsuarioEdit.setError("Complete el nombre");
            nombreUsuarioEdit.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(apellidoUsuarioEdit.getText())) {
            apellidoUsuarioEdit.setError("Complete el apellido");
            apellidoUsuarioEdit.requestFocus();
            return false;
        }

        return true;
    }

    private void setupUI() {
        nombreUsuarioEdit=findViewById(R.id.nombreUsuarioEdit);
        apellidoUsuarioEdit=findViewById(R.id.apellidoUsuarioEdit);
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

        alumno.nombre=nombreUsuarioEdit.getText().toString();
        alumno.apellido=apellidoUsuarioEdit.getText().toString();

        //Invocar servicio
        estudianteService.updateAlumno(alumno, new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                ProgressBar loadingView = findViewById(R.id.loading_mis_datos);
                loadingView.setVisibility(View.INVISIBLE);

                userLogged.firstName=alumno.nombre;
                userLogged.lastName=alumno.apellido;

                userSessionManager.saveUserLogged(userLogged,userSessionManager.getAuthorizationToken());

                DialogBuilder.showAlert("Sus datos han sido actualizado correctamente", "Actualización de Datos", MisDatosActivity.this, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
            }

            @Override
            public void onResponseError(String errorMessage) {
                ProgressBar loadingView = findViewById(R.id.loading_mis_datos);
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
        String messageToDisplay="¿Desea descartar los cambios realizados?";
        showConfirmationAlert(MisDatosActivity.this, "Confirmación de Actualización de Datos", messageToDisplay, "Descartar Cambios","Cancelar",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    goBack();
                }
            });
    }

    public void goBack(){
        super.onBackPressed();
    }
}
