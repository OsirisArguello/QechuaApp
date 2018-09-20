package com.tdp2.quechuaapp.student;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.tdp2.quechuaapp.R;
import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.networking.Client;
import com.tdp2.quechuaapp.networking.EstudianteService;
import com.tdp2.quechuaapp.student.view.CursosAdapter;

import java.util.ArrayList;

public class InscripcionCursoActivity extends AppCompatActivity {

    ArrayList<Curso> cursos;
    EstudianteService estudianteService;

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
                displayCursos();
            }

            @Override
            public void onResponseError() {
                //TODO
            }
        });
    }

    private void displayCursos() {
        final ListView cursosListView = findViewById(R.id.lista_cursos);
        CursosAdapter cursosAdapter = new CursosAdapter(this,cursos);
        cursosListView.setAdapter(cursosAdapter);
    }
}
