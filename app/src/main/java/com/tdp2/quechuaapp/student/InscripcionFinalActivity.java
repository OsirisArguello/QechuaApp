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

import java.util.ArrayList;

public class InscripcionFinalActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Curso curso;
    ArrayList<Final> finales;
    EstudianteService estudianteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        curso = (Curso) getIntent().getSerializableExtra("curso");
        setContentView(R.layout.activity_inscripcion_final);

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

                estudianteService.getMisFinales(new Client() {
                    @Override
                    public void onResponseSuccess(Object responseBody) {

                        ArrayList<InscripcionFinal> misInscripciones = (ArrayList<InscripcionFinal>) responseBody;
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
        String mensaje = aFinal.inscripto ? "desinscribirte?":"inscribirte?";

        new AlertDialog.Builder(this)
                .setTitle("Inscipcion")
                .setMessage("Confirmas que deseas " + mensaje)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (aFinal.inscripto) {
                            Log.i("FINALES", "Desinscripcion a final");
                        } else {
                            Log.i("FINALES", "Inscripcion a final");
                        }
                    }})
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private void displayFinales() {
        final ListView listView = findViewById(R.id.lista_finales);
        listView.setAdapter(new FinalesAdapter(this, finales));
        listView.setOnItemClickListener(this);
        listView.setEmptyView(findViewById(R.id.emptyElementFinales));
    }
}
