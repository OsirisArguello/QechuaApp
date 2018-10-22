package com.tdp2.quechuaapp.student;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tdp2.quechuaapp.MainActivity;
import com.tdp2.quechuaapp.R;
import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.model.Final;
import com.tdp2.quechuaapp.model.InscripcionFinal;
import com.tdp2.quechuaapp.model.Materia;
import com.tdp2.quechuaapp.networking.Client;
import com.tdp2.quechuaapp.networking.EstudianteService;
import com.tdp2.quechuaapp.student.view.FinalesAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class InscripcionFinalActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Curso curso;
    ArrayList<Final> finales;
    EstudianteService estudianteService;
    FinalesAdapter finalesAdapter;
    ArrayList<InscripcionFinal> misInscripciones;

    SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        curso = (Curso) getIntent().getSerializableExtra("curso");
        setContentView(R.layout.activity_inscripcion_final);
        sdf = new SimpleDateFormat("dd/MM/yyyy");

        estudianteService = new EstudianteService();

        setupInitials();
    }

    private void setupInitials() {
        final TextView materiaTextView = findViewById(R.id.materiaTextView);
        final TextView cursoTextView = findViewById(R.id.cursoTextView);
        final TextView cuatrimestreTextView = findViewById(R.id.cuatrimestreTextView);

        materiaTextView.setText("Materia: " + curso.materia.codigo + " - " + curso.materia.nombre);
        cursoTextView.setText("Curso: " + curso.id);

        estudianteService.getFinalesDisponibles(curso.id, new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                finales = (ArrayList<Final>) responseBody;

                getMisInscripciones();
            }

            @Override
            public void onResponseError(String errorMessage) {
                ProgressBar loadingView = findViewById(R.id.loading_inscripcion_final);
                loadingView.setVisibility(View.INVISIBLE);

                showMensajeError(errorMessage);
            }

            @Override
            public Context getContext() {
                return InscripcionFinalActivity.this;
            }
        });
    }

    private void getMisInscripciones() {
        estudianteService.getMisFinales(new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                misInscripciones = (ArrayList<InscripcionFinal>) responseBody;
                for (InscripcionFinal inscripcionFinal: misInscripciones) {
                    if (! inscripcionFinal.estado.equals("ACTIVA")) continue;

                    for (Final otro: finales) {
                        if (otro.id == inscripcionFinal.coloquio.id) {
                            otro.inscripto = true;
                            break;
                        }
                    }
                }

                ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading_inscripcion_final);
                loadingView.setVisibility(View.INVISIBLE);
                displayFinales();
            }

            @Override
            public void onResponseError(String errorMessage) {
                ProgressBar loadingView = findViewById(R.id.loading_inscripcion_final);
                loadingView.setVisibility(View.INVISIBLE);
                Toast.makeText(InscripcionFinalActivity.this, "No fue posible conectarse al servidor, por favor reintente más tarde",
                        Toast.LENGTH_LONG).show();

                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(Toast.LENGTH_LONG); // As I am using LENGTH_LONG in Toast
                            Intent mainActivityIntent = new Intent(InscripcionFinalActivity.this, MainActivity.class);
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
                return InscripcionFinalActivity.this;
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Final aFinal = finales.get(position);
        String mensaje = aFinal.inscripto ? "desinscribirse":"anotarse";



        new AlertDialog.Builder(this)
                .setTitle("Inscripción")
                .setMessage("¿Desea " + mensaje + " a la fecha "+sdf.format(aFinal.fecha)+"?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading_inscripcion_final);
                        loadingView.setVisibility(View.VISIBLE);

                        if (aFinal.inscripto) {
                            Log.i("FINALES", "Desinscripción a final");
                            loadingView.setVisibility(View.INVISIBLE);
                            
                        } else {
                            estudianteService.inscribirFinal(aFinal.id, new Client() {
                                @Override
                                public void onResponseSuccess(Object responseBody) {
                                    ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading_inscripcion_final);
                                    loadingView.setVisibility(View.INVISIBLE);

                                    getMisInscripciones();

                                    showAlert("Ha sido inscripto correctamente a la fecha de final del "+sdf.format(aFinal.fecha),"Inscripción");
                                    finalesAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onResponseError(String errorMessage) {
                                    ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading_inscripcion_final);
                                    loadingView.setVisibility(View.INVISIBLE);
                                    showMensajeError(errorMessage);
                                }

                                @Override
                                public Context getContext() {
                                    return InscripcionFinalActivity.this;
                                }
                            });
                        }
                    }})
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private void displayFinales() {
        final ListView listView = findViewById(R.id.lista_finales);
        finalesAdapter=new FinalesAdapter(this, finales);
        listView.setAdapter(finalesAdapter);
        listView.setOnItemClickListener(this);
        listView.setEmptyView(findViewById(R.id.emptyElementFinales));
    }

    private void showMensajeError(String mensaje) {
        Toast.makeText(InscripcionFinalActivity.this, mensaje, Toast.LENGTH_LONG).show();
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(Toast.LENGTH_LONG);
                    Intent mainActivityIntent = new Intent(InscripcionFinalActivity.this, MainActivity.class);
                    startActivity(mainActivityIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    private void showAlert(String messageToDisplay, String title) {
        AlertDialog alertDialog = new AlertDialog.Builder(InscripcionFinalActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(messageToDisplay);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
