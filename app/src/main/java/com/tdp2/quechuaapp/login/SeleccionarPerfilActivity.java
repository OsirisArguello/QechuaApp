package com.tdp2.quechuaapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.tdp2.quechuaapp.MainActivity;
import com.tdp2.quechuaapp.R;
import com.tdp2.quechuaapp.login.model.UserLogged;
import com.tdp2.quechuaapp.login.model.UserLogged.PerfilActual;
import com.tdp2.quechuaapp.login.model.UserSessionManager;

public class SeleccionarPerfilActivity extends AppCompatActivity {

    private UserSessionManager userSessionManager;
    private UserLogged userLogged;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_perfil);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        userSessionManager=new UserSessionManager(this);
        userLogged=userSessionManager.getUserLogged();

        attachEvents();
    }

    private void attachEvents() {
        LinearLayout perfil_estudiante = findViewById(R.id.perfil_estudiante_action);
        perfil_estudiante.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogged.perfilActual=PerfilActual.ALUMNO;
                userSessionManager.saveUserLogged(userLogged,userSessionManager.getAuthorizationToken());
                Intent intent = new Intent(SeleccionarPerfilActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout perfil_profesor = findViewById(R.id.perfil_profesor_action);
        perfil_profesor.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogged.perfilActual=PerfilActual.PROFESOR;
                userSessionManager.saveUserLogged(userLogged,userSessionManager.getAuthorizationToken());
                Intent intent = new Intent(SeleccionarPerfilActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
