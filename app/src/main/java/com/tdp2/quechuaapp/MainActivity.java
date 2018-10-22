package com.tdp2.quechuaapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tdp2.quechuaapp.login.LoginActivity;
import com.tdp2.quechuaapp.login.model.UserLogged;
import com.tdp2.quechuaapp.login.model.UserSessionManager;
import com.tdp2.quechuaapp.model.Alumno;
import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.model.Materia;
import com.tdp2.quechuaapp.networking.Client;
import com.tdp2.quechuaapp.networking.DocenteService;
import com.tdp2.quechuaapp.networking.EstudianteService;
import com.tdp2.quechuaapp.professor.DetalleCursoActivity;
import com.tdp2.quechuaapp.professor.MostrarCursosDocenteActivity;
import com.tdp2.quechuaapp.student.CursadasActivity;
import com.tdp2.quechuaapp.student.InscripcionCursoActivity;
import com.tdp2.quechuaapp.student.InscripcionMateriasActivity;


public class MainActivity extends AppCompatActivity {

    private UserSessionManager userSessionManager;
    private UserLogged userLogged;
    private EstudianteService estudianteService;
    private DocenteService docenteService;
    private Alumno alumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userSessionManager = new UserSessionManager(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        userLogged=userSessionManager.getUserLogged();

        estudianteService=new EstudianteService();
        docenteService=new DocenteService();

        setupUI();

        setupActions();
    }

    private void setupUI() {

        TextView userNameText = findViewById(R.id.user_logged);
        userNameText.setText(userLogged.firstName+" "+userLogged.lastName);

        TextView emailText = findViewById(R.id.user_logged_email);
        emailText.setText(userLogged.email);



    }

    private void setupActions() {



        final LinearLayout miscursos = findViewById(R.id.miscursos_action);
        LinearLayout misfinales = findViewById(R.id.misfinales_action);

        if(userLogged.authorities.get(0).equals("ROLE_ALUMNO")){


            estudianteService.getAlumno(new Client() {
                @Override
                public void onResponseSuccess(Object responseBody) {
                    alumno=(Alumno) responseBody;
                }

                @Override
                public void onResponseError(String errorMessage) {
                    //TODO
                }

                @Override
                public Context getContext() {
                    return MainActivity.this;
                }
            });

            miscursos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent miscursosIntent = new Intent(MainActivity.this, CursadasActivity.class);

                    /*Materia materia = new Materia();
                    materia.id=1;

                    miscursosIntent.putExtra("alumno",alumno);
                    miscursosIntent.putExtra("materia",materia);*/

                    startActivity(miscursosIntent);
                }
            });

            misfinales.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent misfinalesIntent = new Intent(MainActivity.this, InscripcionCursoActivity.class);
                    //startActivity(misfinalesIntent);
                    Toast.makeText(MainActivity.this, "En Construccion",
                            Toast.LENGTH_LONG).show();
                }
            });

            LinearLayout historiaacademica = findViewById(R.id.historiaacademica_action);
            historiaacademica.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent historiaAcademicaIntent = new Intent(MainActivity.this, InscripcionCursoActivity.class);
                    //startActivity(historiaAcademicaIntent);
                    Toast.makeText(MainActivity.this, "En Construccion",
                            Toast.LENGTH_LONG).show();
                }
            });

            LinearLayout inscripcion = findViewById(R.id.inscripcion_action);
            inscripcion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent inscripcionMateriasIntent = new Intent(MainActivity.this, InscripcionMateriasActivity.class);
                    inscripcionMateriasIntent.putExtra("alumno",alumno);
                    startActivity(inscripcionMateriasIntent);
                }
            });

            LinearLayout prioridad = findViewById(R.id.prioridad_action);
            prioridad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent prioridadIntent = new Intent(MainActivity.this, InscripcionCursoActivity.class);

                    //startActivity(prioridadIntent);
                    Toast.makeText(MainActivity.this, "En Construccion",
                            Toast.LENGTH_LONG).show();
                }
            });
        } else {
            miscursos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detalleCursoIntent = new Intent(MainActivity.this, MostrarCursosDocenteActivity.class);

                    startActivity(detalleCursoIntent);
                }
            });

            misfinales.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent measurementIntent = new Intent(MainActivity.this, InscripcionCursoActivity.class);
                    //startActivity(measurementIntent);
                    Toast.makeText(MainActivity.this, "En Construccion",
                            Toast.LENGTH_LONG).show();
                }
            });
            LinearLayout historiaacademica = findViewById(R.id.historiaacademica_action);
            historiaacademica.setVisibility(View.INVISIBLE);
            LinearLayout inscripcion = findViewById(R.id.inscripcion_action);
            inscripcion.setVisibility(View.INVISIBLE);
            LinearLayout prioridad = findViewById(R.id.prioridad_action);
            prioridad.setVisibility(View.INVISIBLE);


        }

        ImageView logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userSessionManager.logout();
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });




    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }



}