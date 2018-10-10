package com.tdp2.quechuaapp.student;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

public class InscripcionMateriasActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<Carrera> carreras;
    ArrayList<Materia> materias;
    EstudianteService estudianteService;

    ArrayAdapter<CharSequence> carrerasAdapter;
    MateriasAdapter materiasAdapter;

    Spinner carrerasSpinner;

    EditText materiaBuscada;
    ArrayList<Materia> materiasFiltradas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscripcion_materias);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        carrerasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        carrerasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        carrerasSpinner = findViewById(R.id.carreras_spinner);
        carrerasSpinner.setOnItemSelectedListener(this);

        materiaBuscada = findViewById(R.id.materias_search);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(materiaBuscada.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        materiaBuscada.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 3) {
                    filtrarMaterias(s.toString());
                } else if (s.length() == 0) {
                    materiasFiltradas.clear();
                    displayMaterias();
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        estudianteService=new EstudianteService();

        final ListView materiasListView = findViewById(R.id.lista_materias);
        materiasListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent courseSignUpActivity = new Intent(getApplicationContext(), InscripcionCursoActivity.class);
                Alumno alumno = new Alumno();
                alumno.id = 1;
                courseSignUpActivity.putExtra("alumno", alumno);
                ArrayList<Materia> materiasToShow = materiaBuscada.getText().length() == 0 ? materias : materiasFiltradas;
                courseSignUpActivity.putExtra("materia", materiasToShow.get(position));
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

    private void filtrarMaterias(String texto) {
        materiasFiltradas.clear();
        texto = texto.toLowerCase();
        for (Materia materia: materias) {
            if (materia.nombre.toLowerCase().contains(texto)
                    || materia.codigo.toLowerCase().contains(texto)) {
                materiasFiltradas.add(materia);
            }
        }
/*        materiasFiltradas = materias.stream().filter(new Predicate<Materia>() {
            @Override
            public boolean test(Materia materia) {
                return false;
            }
        })
*/      displayMaterias();
    }

    private void displayMaterias() {
        final ListView materiasListView = findViewById(R.id.lista_materias);

        ArrayList<Materia> materiasToShow = materiaBuscada.getText().length() == 0 ? materias : materiasFiltradas;
        materiasAdapter = new MateriasAdapter(this, materiasToShow);
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
