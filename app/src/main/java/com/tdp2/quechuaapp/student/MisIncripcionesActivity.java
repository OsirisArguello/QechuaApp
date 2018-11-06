package com.tdp2.quechuaapp.student;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tdp2.quechuaapp.MainActivity;
import com.tdp2.quechuaapp.R;
import com.tdp2.quechuaapp.model.Alumno;
import com.tdp2.quechuaapp.model.Inscripcion;
import com.tdp2.quechuaapp.networking.Client;
import com.tdp2.quechuaapp.networking.EstudianteService;
import com.tdp2.quechuaapp.student.view.CursosAdapterCallback;
import com.tdp2.quechuaapp.student.view.InscripcionActivaAdapter;
import com.tdp2.quechuaapp.utils.view.DialogBuilder;

import java.util.ArrayList;

public class MisIncripcionesActivity extends AppCompatActivity implements CursosAdapterCallback {

    ArrayList<Inscripcion> inscripciones;
    InscripcionActivaAdapter incripcionesAdapter;

    EstudianteService estudianteService;
    Alumno alumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_incripciones);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        alumno = (Alumno) getIntent().getSerializableExtra("alumno");
        estudianteService = new EstudianteService();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inscripcionMateriasIntent = new Intent(MisIncripcionesActivity.this, InscripcionMateriasActivity.class);
                inscripcionMateriasIntent.putExtra("alumno",alumno);
                startActivity(inscripcionMateriasIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        final ProgressBar loadingView = findViewById(R.id.loading_verinscripciones);
        loadingView.setVisibility(View.VISIBLE);

        estudianteService.getInscripcionesCursos(new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                loadingView.setVisibility(View.INVISIBLE);

                inscripciones = (ArrayList<Inscripcion>) responseBody;
                if (inscripciones.size() == 0) {
                    Intent inscripcionMateriasIntent = new Intent(MisIncripcionesActivity.this, InscripcionMateriasActivity.class);
                    inscripcionMateriasIntent.putExtra("alumno",alumno);
                    MisIncripcionesActivity.this.finish();
                    startActivity(inscripcionMateriasIntent);
                } else {
                    loadInscripciones();
                }
            }

            @Override
            public void onResponseError(String errorMessage) {
                loadingView.setVisibility(View.INVISIBLE);
                Toast.makeText(MisIncripcionesActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }

            @Override
            public Context getContext() {
                return MisIncripcionesActivity.this;
            }
        });
    }

    private void loadInscripciones() {
        final ListView cursosListView = findViewById(R.id.lista_cursos);
        incripcionesAdapter = new InscripcionActivaAdapter(this, inscripciones);
        cursosListView.setAdapter(incripcionesAdapter);
        cursosListView.setEmptyView(findViewById(R.id.emptyElement));
    }

    @Override
    public void desinscribirAlumno(final Integer idInscripcion) {
        final ProgressBar loadingView = findViewById(R.id.loading_verinscripciones);
        loadingView.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        estudianteService.desinscribirAlumno(idInscripcion, new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                loadingView.setVisibility(View.INVISIBLE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                Inscripcion inscripcionEliminada = (Inscripcion) responseBody;

                String messageToDisplay = String.format(getResources().getString(R.string.desinscripcion_exito), inscripcionEliminada.curso.id);

                //Actualizo el curso con la desinscripcion realizada
                for (Inscripcion inscripcion: inscripciones) {
                    if (inscripcion.id.equals(inscripcionEliminada.id)){
                        inscripciones.remove(inscripcion);
                        break;
                    }
                }


                DialogBuilder.showAlert(messageToDisplay, "Desinscripción Satisfactoria",MisIncripcionesActivity.this);
                incripcionesAdapter.notifyDataSetChanged();
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

                DialogBuilder.showAlert(messageToDisplay, "Desinscripción Fallida",MisIncripcionesActivity.this);
            }

            @Override
            public Context getContext() {
                return MisIncripcionesActivity.this;
            }
        });
    }

    @Override
    public void inscribirAlumno(Integer idCurso) {
        Toast.makeText(MisIncripcionesActivity.this, "Accion no disponible", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mainActivityIntent = new Intent(MisIncripcionesActivity.this, MainActivity.class);
        startActivity(mainActivityIntent);

    }
}
