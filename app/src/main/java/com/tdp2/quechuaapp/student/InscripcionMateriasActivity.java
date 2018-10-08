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
import com.tdp2.quechuaapp.model.Materia;
import com.tdp2.quechuaapp.networking.Client;
import com.tdp2.quechuaapp.networking.EstudianteService;
import com.tdp2.quechuaapp.student.view.MateriasAdapter;

import java.util.ArrayList;

public class InscripcionMateriasActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Alumno alumno;
    ArrayList<String> carreras;
    ArrayList<Materia> materias;
    EstudianteService estudianteService;
    MateriasAdapter materiasAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscripcion_materias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner spinner = findViewById(R.id.carreras_spinner);

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // TODO: obtener las carreras del BE
        carreras = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            carreras.add("Carrera " + i);
        }
        adapter.addAll(carreras);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        setupInitials();
    }

    private void setupInitials() {
        materias=new ArrayList<>();
        estudianteService=new EstudianteService();
        alumno=new Alumno();
        alumno.id=1;
        Materia materia =new Materia();
        materia.id=1;
        estudianteService.getMateriasPorCarrera(alumno.id, materia.id, new Client() {
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

    private void displayMaterias() {
        final ListView cursosListView = findViewById(R.id.lista_materias);
        materiasAdapter = new MateriasAdapter(this, materias);
        cursosListView.setAdapter(materiasAdapter);
        cursosListView.setEmptyView(findViewById(R.id.lista_materias_vacia));
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // TODO: obtener las MATERIAS de la CARRERA elegida
        // An item was selected. You can retrieve the selected item using
        Object carrera = parent.getItemAtPosition(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
