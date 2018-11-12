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
import com.tdp2.quechuaapp.model.Cursada;
import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.model.Coloquio;
import com.tdp2.quechuaapp.model.InscripcionColoquio;
import com.tdp2.quechuaapp.networking.Client;
import com.tdp2.quechuaapp.networking.EstudianteService;
import com.tdp2.quechuaapp.student.view.ColoquiosAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class InscripcionColoquioActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Cursada cursada;
    ArrayList<Coloquio> finales;
    EstudianteService estudianteService;
    ColoquiosAdapter coloquiosAdapter;
    ArrayList<InscripcionColoquio> misInscripciones;

    SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cursada = (Cursada) getIntent().getSerializableExtra("cursada");
        setContentView(R.layout.activity_inscripcion_final);
        sdf = new SimpleDateFormat("dd/MM/yyyy");

        estudianteService = new EstudianteService();

        setupInitials();
    }

    private void setupInitials() {
        Curso curso = cursada.curso;

        final TextView materiaTextView = findViewById(R.id.materiaTextView);
        final TextView cursoTextView = findViewById(R.id.cursoTextView);
        final TextView cuatrimestreTextView = findViewById(R.id.cuatrimestreTextView);

        materiaTextView.setText("Materia: " + curso.materia.codigo + " - " + curso.materia.nombre);
        cursoTextView.setText("Curso: " + curso.id);
        cuatrimestreTextView.setText(cursada.periodo.cuatrimestre.toLowerCase() + " cuatrimestre, " + cursada.periodo.anio);

        estudianteService.getFinalesDisponibles(curso.id, new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                finales = (ArrayList<Coloquio>) responseBody;

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
                return InscripcionColoquioActivity.this;
            }
        });
    }

    private void getMisInscripciones() {
        estudianteService.getMisFinales(new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                misInscripciones = (ArrayList<InscripcionColoquio>) responseBody;
                for (InscripcionColoquio inscripcionFinal: misInscripciones) {
                    if (inscripcionFinal.estado.equals("ELIMINADA")){
                        for (Coloquio otro: finales) {
                            if (otro.id == inscripcionFinal.coloquio.id) {
                                otro.inscripto = false;
                                break;
                            }
                        }
                    }

                    for (Coloquio otro: finales) {
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
                Toast.makeText(InscripcionColoquioActivity.this, "No fue posible conectarse al servidor, por favor reintente más tarde",
                        Toast.LENGTH_LONG).show();

                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(Toast.LENGTH_LONG); // As I am using LENGTH_LONG in Toast
                            Intent mainActivityIntent = new Intent(InscripcionColoquioActivity.this, MainActivity.class);
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
                return InscripcionColoquioActivity.this;
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Coloquio aFinal = finales.get(position);

        Date hoy = Calendar.getInstance().getTime();
        long diferencia = aFinal.fecha.getTime() - hoy.getTime();
        diferencia = diferencia / (60 * 60 * 24 * 1000); // Diferencia en dias

        if (diferencia < 2 && !aFinal.inscripto) {
            showAlert("Ya ha pasado el plazo para inscribirse a este final", "Alerta");
            return;
        }

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






                            estudianteService.desinscribirFinal(aFinal.id, new Client() {
                                @Override
                                public void onResponseSuccess(Object responseBody) {
                                    ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading_inscripcion_final);
                                    loadingView.setVisibility(View.INVISIBLE);

                                    for (Coloquio otro : finales) {
                                        if (otro.id == aFinal.id) {
                                            otro.inscripto = false;
                                            break;
                                        }
                                    }
                                    getMisInscripciones();

                                    showAlert("Ha sido desinscripto correctamente a la fecha de final del "+sdf.format(aFinal.fecha),"Desinscripción");
                                    coloquiosAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onResponseError(String errorMessage) {
                                    ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading_inscripcion_final);
                                    loadingView.setVisibility(View.INVISIBLE);
                                    showMensajeError(errorMessage);
                                }

                                @Override
                                public Context getContext() {
                                    return InscripcionColoquioActivity.this;
                                }
                            });
                            
                        } else {
                            estudianteService.inscribirFinal(aFinal.id, new Client() {
                                @Override
                                public void onResponseSuccess(Object responseBody) {
                                    ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading_inscripcion_final);
                                    loadingView.setVisibility(View.INVISIBLE);

                                    getMisInscripciones();

                                    showAlert("Ha sido inscripto correctamente a la fecha de final del "+sdf.format(aFinal.fecha),"Inscripción");
                                    coloquiosAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onResponseError(String errorMessage) {
                                    ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading_inscripcion_final);
                                    loadingView.setVisibility(View.INVISIBLE);
                                    showMensajeError(errorMessage);
                                }

                                @Override
                                public Context getContext() {
                                    return InscripcionColoquioActivity.this;
                                }
                            });
                        }
                    }})
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private void displayFinales() {
        final ListView listView = findViewById(R.id.lista_finales);
        coloquiosAdapter=new ColoquiosAdapter(this, finales);
        listView.setAdapter(coloquiosAdapter);
        listView.setOnItemClickListener(this);
        listView.setEmptyView(findViewById(R.id.emptyElementFinales));
    }

    private void showMensajeError(String mensaje) {
        Toast.makeText(InscripcionColoquioActivity.this, mensaje, Toast.LENGTH_LONG).show();
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(Toast.LENGTH_LONG);
                    Intent mainActivityIntent = new Intent(InscripcionColoquioActivity.this, MainActivity.class);
                    startActivity(mainActivityIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    private void showAlert(String messageToDisplay, String title) {
        AlertDialog alertDialog = new AlertDialog.Builder(InscripcionColoquioActivity.this).create();
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
