package com.tdp2.quechuaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tdp2.quechuaapp.model.Alumno;
import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.model.Materia;
import com.tdp2.quechuaapp.professor.DetalleCursoActivity;
import com.tdp2.quechuaapp.student.InscripcionCursoActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        attachEvents();

    }

    private void attachEvents() {

        Button studentSignUpButton1 = findViewById(R.id.estudiante_materia1);
        studentSignUpButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent courseSignUpActivity = new Intent(getApplicationContext(), InscripcionCursoActivity.class);
                Alumno alumno=new Alumno();
                alumno.id=1;
                Materia materia =new Materia();
                materia.id=1;
                courseSignUpActivity.putExtra("alumno",alumno);
                courseSignUpActivity.putExtra("materia", materia);
                startActivity(courseSignUpActivity);
            }
        });

        Button studentSignUpButton2 = findViewById(R.id.estudiante_materia2);
        studentSignUpButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent courseSignUpActivity = new Intent(getApplicationContext(), InscripcionCursoActivity.class);
                Alumno alumno=new Alumno();
                alumno.id=1;
                Materia materia =new Materia();
                materia.id=2;
                courseSignUpActivity.putExtra("alumno",alumno);
                courseSignUpActivity.putExtra("materia", materia);
                startActivity(courseSignUpActivity);
            }
        });

        Button studentSignUpButton3 = findViewById(R.id.estudiante_materia3);
        studentSignUpButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent courseSignUpActivity = new Intent(getApplicationContext(), InscripcionCursoActivity.class);
                Alumno alumno=new Alumno();
                alumno.id=2;
                Materia materia =new Materia();
                materia.id=2;
                courseSignUpActivity.putExtra("alumno",alumno);
                courseSignUpActivity.putExtra("materia", materia);
                startActivity(courseSignUpActivity);
            }
        });

        Button studentCursadasButton = findViewById(R.id.estudiante_cursadas_view);
        studentCursadasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent courseSignUpActivity = new Intent(getApplicationContext(), InscripcionCursoActivity.class);
                Alumno alumno=new Alumno();
                alumno.id=1;
                courseSignUpActivity.putExtra("alumno",alumno);
                startActivity(courseSignUpActivity);
            }
        });

        Button professorCoursesView1 = findViewById(R.id.professor_course_view_button1);
        professorCoursesView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent courseViewActivity = new Intent(getApplicationContext(), DetalleCursoActivity.class);
                Curso curso = new Curso();
                curso.id=1;
                courseViewActivity.putExtra("curso",curso);
                startActivity(courseViewActivity);
            }
        });

        Button professorCoursesView2 = findViewById(R.id.professor_course_view_button2);
        professorCoursesView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent courseViewActivity = new Intent(getApplicationContext(), DetalleCursoActivity.class);
                Curso curso = new Curso();
                curso.id=2;
                courseViewActivity.putExtra("curso",curso);
                startActivity(courseViewActivity);
            }
        });
    }
}
