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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class InscripcionMateriasActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Carrera carreraSeleccionada;
    ArrayList<Carrera> carreras;
    HashMap<Carrera, ArrayList<Materia>> materiasPorCarrera = new HashMap<>();
    EstudianteService estudianteService;

    ArrayAdapter<CharSequence> carrerasAdapter;
    MateriasAdapter materiasAdapter;

    Spinner carrerasSpinner;

    EditText materiaBuscada;
    ArrayList<Materia> materiasFiltradas = new ArrayList<>();
    Alumno alumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscripcion_materias);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        alumno = (Alumno) getIntent().getSerializableExtra("alumno");

        estudianteService = new EstudianteService();

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
                } else if (s.length() == 0 && materiasFiltradas.size() > 0) {
                    materiasFiltradas.clear();
                    displayMaterias();
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });


        final ListView materiasListView = findViewById(R.id.lista_materias);
        materiasListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent courseSignUpActivity = new Intent(getApplicationContext(), InscripcionCursoActivity.class);
                courseSignUpActivity.putExtra("alumno", alumno);
                courseSignUpActivity.putExtra("materia", materias().get(position));
                startActivity(courseSignUpActivity);
            }
        });
        materiasListView.setEmptyView(findViewById(R.id.lista_materias_vacia));

        setupInitials();
    }

    private Boolean filtroActivo() {
        return materiaBuscada.getText().length() != 0;
    }

    private ArrayList<Materia> materias() {
        return filtroActivo() ? materiasFiltradas : materiasPorCarrera.get(carreraSeleccionada);
    }

    private void setupInitials() {
        estudianteService.getCarreras(new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                carreras = (ArrayList<Carrera>) responseBody;
                carreraSeleccionada = carreras.get(0);

                ArrayList<CharSequence> aux = new ArrayList<>();
                for (Carrera carrera : carreras) {
                    aux.add(carrera.nombre);
                }

                carrerasAdapter.addAll(aux);
                carrerasSpinner.setAdapter(carrerasAdapter);
            }

            @Override
            public void onResponseError(String errorMessage) {
                showMensajeError(errorMessage);
            }

            @Override
            public Context getContext() {
                return InscripcionMateriasActivity.this;
            }
        });
    }

    private void filtrarMaterias(String texto) {
        materiasFiltradas.clear();
        texto = texto.toLowerCase();
        final ArrayList<Materia> materias = materiasPorCarrera.get(carreraSeleccionada);
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

        materiasAdapter = new MateriasAdapter(this, materias());
        materiasListView.setAdapter(materiasAdapter);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        materiasFiltradas.clear();
        materiaBuscada.setText("");

        carreraSeleccionada = carreras.get(pos);
        if (materiasPorCarrera.containsKey(carreraSeleccionada) && materiasPorCarrera.get(carreraSeleccionada) != null) {
            displayMaterias();
        } else {
            estudianteService.getMateriasPorCarrera(carreraSeleccionada.id, new Client() {
                @Override
                public void onResponseSuccess(Object responseBody) {
                    ArrayList<Materia> materias = (ArrayList<Materia>) responseBody;
                    Collections.sort(materias, new Comparator<Materia>() {
                        @Override
                        public int compare(Materia materia1, Materia materia2) {
                            return materia1.codigo.compareTo(materia2.codigo);
                        }
                    });
                    materiasPorCarrera.put(carreraSeleccionada, materias);
                    displayMaterias();
                }

                @Override
                public void onResponseError(String errorMessage) {
                    showMensajeError(errorMessage);
                }

                @Override
                public Context getContext() {
                    return InscripcionMateriasActivity.this;
                }
            });
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private void showMensajeError(String mensaje) {
        Toast.makeText(InscripcionMateriasActivity.this, mensaje, Toast.LENGTH_LONG).show();

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
}
