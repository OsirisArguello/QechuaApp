package com.tdp2.quechuaapp.student;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tdp2.quechuaapp.MainActivity;
import com.tdp2.quechuaapp.model.Alumno;
import com.tdp2.quechuaapp.model.Inscripcion;
import com.tdp2.quechuaapp.model.Materia;
import com.tdp2.quechuaapp.student.view.CursosAdapterCallback;
import com.tdp2.quechuaapp.R;
import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.networking.Client;
import com.tdp2.quechuaapp.networking.EstudianteService;
import com.tdp2.quechuaapp.student.view.CursosAdapter;
import com.tdp2.quechuaapp.utils.view.DialogBuilder;

import java.util.ArrayList;

public class InscripcionCursoActivity extends AppCompatActivity implements CursosAdapterCallback {

    Alumno alumno;
    Materia materia;
    ArrayList<Curso> cursos;
    EstudianteService estudianteService;
    CursosAdapter cursosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        alumno = (Alumno) getIntent().getSerializableExtra("alumno");
        materia = (Materia) getIntent().getSerializableExtra("materia");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscripcion_curso);

        setupInitials();
    }

    private void setupInitials() {
        cursos=new ArrayList<>();
        estudianteService=new EstudianteService();
        getCursosPorMateria();
    }

    private void getCursosPorMateria(){
        estudianteService.getCursosPorMateria(materia.id, new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                cursos=(ArrayList<Curso>) responseBody;
                ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading_inscripcion_curso);
                loadingView.setVisibility(View.INVISIBLE);
                displayCursos();
            }

            @Override
            public void onResponseError(String errorMessage) {
                ProgressBar loadingView = findViewById(R.id.loading_inscripcion_curso);
                loadingView.setVisibility(View.INVISIBLE);
                Toast.makeText(InscripcionCursoActivity.this, "No fue posible conectarse al servidor, por favor reintente más tarde",
                        Toast.LENGTH_LONG).show();

                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(Toast.LENGTH_LONG); // As I am using LENGTH_LONG in Toast
                            Intent mainActivityIntent = new Intent(InscripcionCursoActivity.this, MainActivity.class);
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
                return InscripcionCursoActivity.this;
            }
        });
    }

    private void displayCursos() {
        final ListView cursosListView = findViewById(R.id.lista_cursos);
        cursosAdapter = new CursosAdapter(this, cursos, alumno);
        cursosListView.setAdapter(cursosAdapter);
        cursosListView.setEmptyView(findViewById(R.id.emptyElement));
    }

    @Override
    public void inscribirAlumno(final Integer idCurso) {

        ProgressBar loadingView = findViewById(R.id.loading_inscripcion_curso);
        loadingView.setVisibility(View.VISIBLE);
        loadingView.bringToFront();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


        estudianteService.inscribirAlumno(idCurso, new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                ProgressBar loadingView = findViewById(R.id.loading_inscripcion_curso);
                loadingView.setVisibility(View.INVISIBLE);

                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                Inscripcion inscripcion = (Inscripcion) responseBody;

                String messageToDisplay;

                if (inscripcion.estado.equals("REGULAR")){
                    messageToDisplay = String.format(getResources().getString(R.string.inscripcion_exito), inscripcion.curso.id);
                } else {
                    messageToDisplay = String.format(getResources().getString(R.string.inscripcion_exito_condicional), inscripcion.curso.id);
                }

                //Actualizo el curso con la inscripcion realizada
                /*for (Curso curso : cursos) {
                    if(curso.id.equals(idCurso)){
                        curso.inscripciones.add(inscripcion);
                        //Caso especial en el que se anota habiendo vacantes pero se acabaron las vacantes antes de que presione el boton de inscripcion
                        if (curso.getVacantes()>0 && inscripcion.estado.equals("CONDICIONAL")){
                            curso.capacidadCurso=0;
                        }
                    }
                }*/

                getCursosPorMateria();

                DialogBuilder.showAlert(messageToDisplay, "Inscripción Satisfactoria",InscripcionCursoActivity.this);
                cursosAdapter.notifyDataSetChanged();

            }

            @Override
            public void onResponseError(String errorMessage) {
                ProgressBar loadingView = findViewById(R.id.loading_inscripcion_curso);
                loadingView.setVisibility(View.INVISIBLE);

                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                //TODO manejar los distintos problemas de inscripcion y cual es el mensaje que se va a mostrar al usuario
                String messageToDisplay;
                if(errorMessage!=null){
                    Integer idError = getResources().getIdentifier(errorMessage,"string",getPackageName());
                    messageToDisplay=getString(idError);
                } else {
                    messageToDisplay=getString(R.string.inscripcion_error_generico);
                }

                DialogBuilder.showAlert(messageToDisplay, "Inscripción Fallida",InscripcionCursoActivity.this);
                cursosAdapter.notifyDataSetChanged();
            }

            @Override
            public Context getContext() {
                return InscripcionCursoActivity.this;
            }
        });

    }

    @Override
    public void desinscribirAlumno(final Integer idInscripcion) {

        ProgressBar loadingView = findViewById(R.id.loading_inscripcion_curso);
        loadingView.setVisibility(View.VISIBLE);
        loadingView.bringToFront();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


        estudianteService.desinscribirAlumno(idInscripcion, new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                ProgressBar loadingView = findViewById(R.id.loading_inscripcion_curso);
                loadingView.setVisibility(View.INVISIBLE);

                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                Inscripcion inscripcion = (Inscripcion) responseBody;

                String messageToDisplay = String.format(getResources().getString(R.string.desinscripcion_exito), inscripcion.curso.id);

                /*//Actualizo el curso con la desinscripcion realizada
                for (Curso curso : cursos) {
                    if(curso.id.equals(inscripcion.curso.id)){
                        curso.inscripciones.remove(inscripcion);
                    }
                }*/
                getCursosPorMateria();


                DialogBuilder.showAlert(messageToDisplay, "Desinscripción Satisfactoria",InscripcionCursoActivity.this);
                cursosAdapter.notifyDataSetChanged();

            }

            @Override
            public void onResponseError(String errorMessage) {
                ProgressBar loadingView = findViewById(R.id.loading_inscripcion_curso);
                loadingView.setVisibility(View.INVISIBLE);

                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                //TODO manejar los distintos problemas de inscripcion y cual es el mensaje que se va a mostrar al usuario
                String messageToDisplay;
                if(errorMessage!=null){
                    Integer idError = getResources().getIdentifier(errorMessage,"string",getPackageName());
                    messageToDisplay=getString(idError);
                } else {
                    messageToDisplay=getString(R.string.desinscripcion_error_generico);
                }

                DialogBuilder.showAlert(messageToDisplay, "Desinscripción Fallida",InscripcionCursoActivity.this);
                cursosAdapter.notifyDataSetChanged();

            }

            @Override
            public Context getContext() {
                return InscripcionCursoActivity.this;
            }
        });

    }

    /*private void showAlert(String messageToDisplay, String title) {
        AlertDialog alertDialog = new AlertDialog.Builder(InscripcionCursoActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(messageToDisplay);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
        cursosAdapter.notifyDataSetChanged();
    }*/
}
