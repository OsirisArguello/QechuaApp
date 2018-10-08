package com.tdp2.quechuaapp.student;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.tdp2.quechuaapp.MainActivity;
import com.tdp2.quechuaapp.R;
import com.tdp2.quechuaapp.model.Alumno;
import com.tdp2.quechuaapp.model.Carrera;
import com.tdp2.quechuaapp.model.Materia;
import com.tdp2.quechuaapp.networking.Client;
import com.tdp2.quechuaapp.networking.EstudianteService;
import com.tdp2.quechuaapp.student.view.MateriasAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InscripcionMateriasActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<Carrera> carreras;
    ArrayList<Materia> materias;
    EstudianteService estudianteService;

    ArrayAdapter<CharSequence> carrerasAdapter;
    MateriasAdapter materiasAdapter;

    Spinner carrerasSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscripcion_materias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        carrerasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        carrerasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        carrerasSpinner = findViewById(R.id.carreras_spinner);
        carrerasSpinner.setOnItemSelectedListener(this);

        estudianteService=new EstudianteService();

        final ListView materiasListView = findViewById(R.id.lista_materias);
        materiasListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent courseSignUpActivity = new Intent(getApplicationContext(), InscripcionCursoActivity.class);
                Alumno alumno = new Alumno();
                alumno.id = 1;
                courseSignUpActivity.putExtra("alumno", alumno);
                courseSignUpActivity.putExtra("materia", materias.get(position));
                startActivity(courseSignUpActivity);
            }
        });

        setupInitials();
    }

    private void setupInitials() {
        loadCarreras();

        materias=new ArrayList<>();

        estudianteService.getMaterias(new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                materias=(ArrayList<Materia>) responseBody;
                displayMaterias();
            }

            @Override
            public void onResponseError(String errorMessage) {
                Toast.makeText(InscripcionMateriasActivity.this, "No fue posible conectarse al servidor, por favor reintente más tarde",
                        Toast.LENGTH_LONG).show();

                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(Toast.LENGTH_LONG); // As I am using LENGTH_LONG in Toast
                            Intent mainActivityIntent = new Intent(InscripcionMateriasActivity.this, MainActivity.class);
                            startActivity(mainActivityIntent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }
        });
    }

    private void loadCarreras() {
        // TODO: obtener las carreras del BE
        carreras = new ArrayList<>();
        ArrayList<CharSequence> aux = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Carrera carrera = new Carrera();
            carrera.id = i;
            carrera.nombre = "Carrera " + i;

            aux.add("Carrera " + i);
            carreras.add(carrera);
        }

/*        carrerasAdapter.addAll(carreras.stream().map(new Function<Carrera, String>() {
            public String apply(final Carrera carrera){
                return carrera.nombre;
            }
        }).collect(Collectors.toCollection()));
*/
        carrerasAdapter.addAll(aux);
        carrerasSpinner.setAdapter(carrerasAdapter);
    }

    private void displayMaterias() {
        final ListView materiasListView = findViewById(R.id.lista_materias);
        materiasAdapter = new MateriasAdapter(this, materias);
        materiasListView.setAdapter(materiasAdapter);
        materiasListView.setEmptyView(findViewById(R.id.lista_materias_vacia));
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // TODO: obtener las MATERIAS de la CARRERA elegida
        Carrera carrera = carreras.get(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
