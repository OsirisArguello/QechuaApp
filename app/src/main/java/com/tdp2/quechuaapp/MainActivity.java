package com.tdp2.quechuaapp;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdp2.quechuaapp.login.model.UserLogged;
import com.tdp2.quechuaapp.login.model.UserSessionManager;
import com.tdp2.quechuaapp.professor.DetalleCursoActivity;
import com.tdp2.quechuaapp.student.InscripcionCursoActivity;
import com.tdp2.quechuaapp.student.InscripcionMateriasActivity;


public class MainActivity extends AppCompatActivity {

    private UserSessionManager userSessionManager;
    private UserLogged userLogged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userSessionManager = new UserSessionManager(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        userLogged=userSessionManager.getUserLogged();

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

        LinearLayout miscursos = findViewById(R.id.miscursos_action);
        if(userLogged.authorities.get(0).equals("ROLE_ALUMNO")){
            miscursos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent measurementIntent = new Intent(MainActivity.this, InscripcionCursoActivity.class);
                    startActivity(measurementIntent);
                }
            });
        } else {
            miscursos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent measurementIntent = new Intent(MainActivity.this, DetalleCursoActivity.class);
                    startActivity(measurementIntent);
                }
            });
        }
        LinearLayout misfinales = findViewById(R.id.misfinales_action);

        if(userLogged.authorities.get(0).equals("ROLE_ALUMNO")){
            misfinales.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent measurementIntent = new Intent(MainActivity.this, InscripcionCursoActivity.class);
                    startActivity(measurementIntent);
                }
            });
        } else {
            misfinales.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent measurementIntent = new Intent(MainActivity.this, InscripcionCursoActivity.class);
                    startActivity(measurementIntent);
                }
            });
        }

        LinearLayout historiaacademica = findViewById(R.id.historiaacademica_action);
        historiaacademica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent measurementIntent = new Intent(MainActivity.this, InscripcionCursoActivity.class);
                startActivity(measurementIntent);
            }
        });

        LinearLayout inscripcion = findViewById(R.id.inscripcion_action);
        inscripcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inscripcionMateriasIntent = new Intent(MainActivity.this, InscripcionMateriasActivity.class);
                startActivity(inscripcionMateriasIntent);
            }
        });

        LinearLayout prioridad = findViewById(R.id.prioridad_action);
        prioridad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent measurementIntent = new Intent(MainActivity.this, InscripcionCursoActivity.class);
                startActivity(measurementIntent);
            }
        });

        if(!userLogged.authorities.get(0).equals("ROLE_ALUMNO")) {
            historiaacademica.setVisibility(View.INVISIBLE);
            inscripcion.setVisibility(View.INVISIBLE);
            prioridad.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onBackPressed() {
        /*DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        /*getMenuInflater().inflate(R.menu.main, menu);

        UserLogged user = userSessionManager.getUserLogged();

        menu.getItem(0).setTitle(user.toString());
        menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.close));*/

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*int id = item.getItemId();

        if (id == R.id.logout) {
            userSessionManager.logout();
            finish();
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }



}