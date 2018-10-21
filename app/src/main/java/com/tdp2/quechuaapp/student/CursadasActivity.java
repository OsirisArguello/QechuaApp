package com.tdp2.quechuaapp.student;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tdp2.quechuaapp.MainActivity;
import com.tdp2.quechuaapp.R;
import com.tdp2.quechuaapp.model.Alumno;
import com.tdp2.quechuaapp.model.Cursada;
import com.tdp2.quechuaapp.model.Inscripcion;
import com.tdp2.quechuaapp.networking.Client;
import com.tdp2.quechuaapp.networking.EstudianteService;
import com.tdp2.quechuaapp.student.view.CursadasAdapter;
import com.tdp2.quechuaapp.student.view.CursadasAdapterCallback;

import java.util.ArrayList;

public class CursadasActivity extends AppCompatActivity implements CursadasAdapterCallback {

    ArrayList<Cursada> cursadas;
    EstudianteService estudianteService;
    CursadasAdapter cursadasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursadas);
        setupInitials();
    }

    private void setupInitials() {
        cursadas=new ArrayList<>();
        estudianteService=new EstudianteService();
        /*cursos = estudianteService.getCursadasMock();
        ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading_inscripcion_curso);
        loadingView.setVisibility(View.INVISIBLE);
        displayCursos();*/
        estudianteService.getCursadas(new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                cursadas=(ArrayList<Cursada>) responseBody;
                ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading_cursadas);
                loadingView.setVisibility(View.INVISIBLE);
                displayCursadas();
            }

            @Override
            public void onResponseError(String errorMessage) {
                ProgressBar loadingView = findViewById(R.id.loading_cursadas);
                loadingView.setVisibility(View.INVISIBLE);
                Toast.makeText(CursadasActivity.this, "No fue posible conectarse al servidor, por favor reintente más tarde",
                        Toast.LENGTH_LONG).show();

                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(Toast.LENGTH_LONG); // As I am using LENGTH_LONG in Toast
                            Intent mainActivityIntent = new Intent(CursadasActivity.this, MainActivity.class);
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
                return CursadasActivity.this;
            }
        });
    }

    private void displayCursadas() {
        final ListView cursadasListView = findViewById(R.id.lista_cursadas);
        cursadasAdapter = new CursadasAdapter(this, cursadas);
        cursadasListView.setAdapter(cursadasAdapter);
        cursadasListView.setEmptyView(findViewById(R.id.emptyElementCursadas));
    }

    @Override
    public void desinscribirAlumno(final Integer idCursada, final Button desinscribirseButton) {

        ProgressBar loadingView = findViewById(R.id.loading_cursadas);
        loadingView.setVisibility(View.VISIBLE);
        loadingView.bringToFront();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        estudianteService.desinscribirAlumno(idCursada, new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                ProgressBar loadingView = findViewById(R.id.loading_cursadas);
                loadingView.setVisibility(View.INVISIBLE);

                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                Inscripcion inscripcion = (Inscripcion) responseBody;

                String messageToDisplay;

                messageToDisplay = String.format(getResources().getString(R.string.desinscripcion_exito), inscripcion.curso.id);


                //Actualizo el curso con la desinscripcion realizada
                //for (Cursada cursada : cursadas) {
                //    if(cursadas.curso.id.equals(idCurso)){
                //        cursadas.curso.inscripciones.remove(inscripcion);
                //    }
                //}

                showAlert(messageToDisplay, "Desinscripción Satisfactoria");
            }

            @Override
            public void onResponseError(String errorMessage) {
                ProgressBar loadingView = findViewById(R.id.loading_cursadas);
                loadingView.setVisibility(View.INVISIBLE);

                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                String messageToDisplay;
                if(errorMessage!=null){
                    Integer idError = getResources().getIdentifier(errorMessage,"string",getPackageName());
                    messageToDisplay=getString(idError);
                } else {
                    messageToDisplay=getString(R.string.desinscripcion_error_generico);
                }

                showAlert(messageToDisplay, "Desinscripción Fallida");

            }

            @Override
            public Context getContext() {
                return CursadasActivity.this;
            }
        });

    }

    private void showAlert(String messageToDisplay, String title) {
        AlertDialog alertDialog = new AlertDialog.Builder(CursadasActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(messageToDisplay);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
        cursadasAdapter.notifyDataSetChanged();
    }
}
