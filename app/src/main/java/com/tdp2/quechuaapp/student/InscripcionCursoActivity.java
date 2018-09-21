package com.tdp2.quechuaapp.student;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tdp2.quechuaapp.MainActivity;
import com.tdp2.quechuaapp.model.Inscripcion;
import com.tdp2.quechuaapp.student.view.CursosAdapterCallback;
import com.tdp2.quechuaapp.R;
import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.networking.Client;
import com.tdp2.quechuaapp.networking.EstudianteService;
import com.tdp2.quechuaapp.student.view.CursosAdapter;

import java.util.ArrayList;

public class InscripcionCursoActivity extends AppCompatActivity implements CursosAdapterCallback {

    ArrayList<Curso> cursos;
    EstudianteService estudianteService;
    CursosAdapter cursosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscripcion_curso);

        setupInitials();
    }

    private void setupInitials() {
        cursos=new ArrayList<>();
        estudianteService=new EstudianteService();
        estudianteService.getCursos(0, new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                cursos=(ArrayList<Curso>) responseBody;
                ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading_inscripcion_curso);
                loadingView.setVisibility(View.INVISIBLE);
                displayCursos();
            }

            @Override
            public void onResponseError() {
                ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading_inscripcion_curso);
                loadingView.setVisibility(View.INVISIBLE);
                Toast.makeText(InscripcionCursoActivity.this, "No fue posible conectarse al servidor, por favor reintente más tarde",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void displayCursos() {
        final ListView cursosListView = findViewById(R.id.lista_cursos);
        cursosAdapter = new CursosAdapter(this,cursos);
        cursosListView.setAdapter(cursosAdapter);
    }

    @Override
    public void inscribirAlumno(Integer idAlumno, Integer idCurso) {

        estudianteService.inscribirAlumno(idAlumno, idCurso, new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {

                Inscripcion inscripcion = (Inscripcion) responseBody;

                AlertDialog alertDialog = new AlertDialog.Builder(InscripcionCursoActivity.this).create();
                alertDialog.setTitle("Inscripción Satisfactoria");
                alertDialog.setMessage("Usted ha quedado inscripto como "+inscripcion.estado.toUpperCase()+
                        " en el curso de la materia "+inscripcion.curso.materia.nombre+" del docente"+inscripcion.curso.profesor.apellido);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                cursosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onResponseError() {
                //TODO manejar los distintos problemas de inscripcion y cual es el mensaje que se va a mostrar al usuario
                AlertDialog alertDialog = new AlertDialog.Builder(InscripcionCursoActivity.this).create();
                alertDialog.setTitle("Inscripción Fallida");
                alertDialog.setMessage("Usted no ha podido ser inscripto en esta materia. Por favor, intente nuevamente.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                cursosAdapter.notifyDataSetChanged();
            }
        });

    }
}
