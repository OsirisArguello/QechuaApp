package com.tdp2.quechuaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        Button studentSignUpButton = findViewById(R.id.student_signup_button);
        studentSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent courseSignUpActivity = new Intent(getApplicationContext(), InscripcionCursoActivity.class);
                startActivity(courseSignUpActivity);
            }
        });

        Button professorSignUpButton = findViewById(R.id.professor_course_view_button);
        professorSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent courseViewActivity = new Intent(getApplicationContext(), DetalleCursoActivity.class);
                startActivity(courseViewActivity);
            }
        });
    }
}
