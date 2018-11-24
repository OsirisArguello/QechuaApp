package com.tdp2.quechuaapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tdp2.quechuaapp.login.LoginActivity;
import com.tdp2.quechuaapp.login.SeleccionarPerfilActivity;
import com.tdp2.quechuaapp.login.model.UserLogged;
import com.tdp2.quechuaapp.login.model.UserLogged.PerfilActual;
import com.tdp2.quechuaapp.login.model.UserSessionManager;
import com.tdp2.quechuaapp.model.Alumno;

import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.model.Materia;
import com.tdp2.quechuaapp.model.PeriodoActividad;
import com.tdp2.quechuaapp.model.PeriodoAdministrativo;

import com.tdp2.quechuaapp.networking.Client;
import com.tdp2.quechuaapp.networking.DocenteService;
import com.tdp2.quechuaapp.networking.EstudianteService;
import com.tdp2.quechuaapp.professor.MostrarCursosDocenteActivity;
import com.tdp2.quechuaapp.student.CursadasActivity;
import com.tdp2.quechuaapp.student.MisFinalesActivity;
import com.tdp2.quechuaapp.student.InscripcionMateriasActivity;
import com.tdp2.quechuaapp.student.MisIncripcionesActivity;
import com.tdp2.quechuaapp.student.PrioridadActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 2;
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

        updateAccionesPeriodo();

        setupUI();

        setupActions();
    }

    private void setupUI() {

        TextView userNameText = findViewById(R.id.user_logged);
        userNameText.setText(userLogged.firstName+" "+userLogged.lastName);

        TextView emailText = findViewById(R.id.user_logged_email);
        emailText.setText(userLogged.email);
        ImageView cambiarPerfil=findViewById(R.id.cambiar_perfil);

        if(userLogged.authorities.size()<2){
            cambiarPerfil.setVisibility(View.INVISIBLE);
        } else {
            cambiarPerfil.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, SeleccionarPerfilActivity.class);
                    startActivity(intent);
                }
            });
        }

    }

    private void setupActions() {



        final LinearLayout miscursos = findViewById(R.id.miscursos_action);
        LinearLayout misfinales = findViewById(R.id.misfinales_action);

        // Si el usuario loggeado teneia un perfil invalido
        if (userLogged.perfilActual == null) {
            userSessionManager.logout();
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            return;
        }

        if(userLogged.perfilActual.equals(PerfilActual.ALUMNO)){
            //ES ALUMNO

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
                    startActivity(miscursosIntent);
                }
            });

            misfinales.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent misfinalesIntent = new Intent(MainActivity.this, MisFinalesActivity.class);
                    startActivity(misfinalesIntent);
                }
            });

            LinearLayout historiaacademica = findViewById(R.id.historiaacademica_action);
            historiaacademica.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "En Construccion",
                            Toast.LENGTH_LONG).show();
                }
            });

            LinearLayout inscripcion = findViewById(R.id.inscripcion_action);
            inscripcion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<PeriodoActividad>actividades = userSessionManager.getActividadValida();
                    if (actividades.contains(PeriodoActividad.INSCRIPCION_CURSADA)
                            || actividades.contains(PeriodoActividad.DESINSCRIPCION_CURSADA)) {
                        Intent inscripcionMateriasIntent = new Intent(MainActivity.this, MisIncripcionesActivity.class);
                        inscripcionMateriasIntent.putExtra("alumno",alumno);
                        startActivity(inscripcionMateriasIntent);

                    } else {
                        Toast.makeText(MainActivity.this, "La inscripcion no esta habilitada aun",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });

            LinearLayout prioridad = findViewById(R.id.prioridad_action);
            prioridad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent prioridadIntent = new Intent(MainActivity.this, PrioridadActivity.class);
                    startActivity(prioridadIntent);
                }
            });
        } else {

            //ES DOCENTE
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

    private void updateAccionesPeriodo() {
        Client client = new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                ArrayList<PeriodoAdministrativo> actividades = (ArrayList<PeriodoAdministrativo>)responseBody;
                userSessionManager.saveActividadValida(actividades);
            }

            @Override
            public void onResponseError(String errorMessage) {
                Toast.makeText(MainActivity.this,
                        "No se pudieron obtener las acciones disponibles para el periodo.\n Algunas acciones podrian no estar disponibles",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public Context getContext() {
                return MainActivity.this;
            }
        };

        if (userLogged.perfilActual.equals(PerfilActual.ALUMNO)) {
            estudianteService.getAccionesPeriodo(client);
        } else {
            docenteService.getAccionesPeriodo(client);
        }
    }

    public void verMisDatos(View v){

        if (userLogged.perfilActual.equals(PerfilActual.ALUMNO)) {
            Intent misDatosIntent = new Intent(MainActivity.this, MisDatosActivity.class);
            misDatosIntent.putExtra("alumno",alumno);

            startActivityForResult(misDatosIntent, REQUEST_CODE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        userLogged=userSessionManager.getUserLogged();
        setupUI();
    }

}