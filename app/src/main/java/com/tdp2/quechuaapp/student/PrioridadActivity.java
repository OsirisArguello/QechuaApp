package com.tdp2.quechuaapp.student;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tdp2.quechuaapp.MainActivity;
import com.tdp2.quechuaapp.R;
import com.tdp2.quechuaapp.model.Prioridad;
import com.tdp2.quechuaapp.networking.Client;
import com.tdp2.quechuaapp.networking.EstudianteService;
import com.tdp2.quechuaapp.student.view.PrioridadAdapter;

import java.util.ArrayList;

public class PrioridadActivity extends AppCompatActivity  {

    // Se efectua un array de prioridades por si en el futuro se muestra el historial de prioridades
    ArrayList<Prioridad> prioridades;
    EstudianteService estudianteService;
    PrioridadAdapter prioridadAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prioridad);
        setupInitials();
    }

    private void setupInitials() {
        prioridades=new ArrayList<>();
        estudianteService=new EstudianteService();

        estudianteService.getPrioridad(new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                prioridades=(ArrayList<Prioridad>) responseBody;
                ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading_prioridad);
                loadingView.setVisibility(View.INVISIBLE);
                displayPrioridad();
            }

            @Override
            public void onResponseError(String errorMessage) {
                ProgressBar loadingView = findViewById(R.id.loading_prioridad);
                loadingView.setVisibility(View.INVISIBLE);
                Toast.makeText(PrioridadActivity.this, "No fue posible conectarse al servidor, por favor reintente más tarde",
                        Toast.LENGTH_LONG).show();

                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(Toast.LENGTH_LONG); // As I am using LENGTH_LONG in Toast
                            Intent mainActivityIntent = new Intent(PrioridadActivity.this, MainActivity.class);
                            startActivity(mainActivityIntent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }

            @Override
            public Context getContext() {
                return PrioridadActivity.this;
            }
        });
    }

    private void displayPrioridad() {
        final ListView prioridadListView = findViewById(R.id.lista_prioridad);
        prioridadAdapter = new PrioridadAdapter(this, prioridades);
        prioridadListView.setAdapter(prioridadAdapter);
        prioridadListView.setEmptyView(findViewById(R.id.emptyElementPrioridad));
    }


    private void showAlert(String messageToDisplay, String title) {
        AlertDialog alertDialog = new AlertDialog.Builder(PrioridadActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(messageToDisplay);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
        prioridadAdapter.notifyDataSetChanged();
    }
}
