package com.tdp2.quechuaapp.student;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tdp2.quechuaapp.MainActivity;
import com.tdp2.quechuaapp.R;
import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.model.Final;
import com.tdp2.quechuaapp.networking.Client;
import com.tdp2.quechuaapp.networking.EstudianteService;

import java.util.ArrayList;

public class InscripcionFinalActivity extends AppCompatActivity {

    Curso curso;
    ArrayList<Final> finales;
    EstudianteService estudianteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        curso = (Curso) getIntent().getSerializableExtra("curso");
        setContentView(R.layout.activity_inscripcion_final);

        setupInitials();
    }

    private void setupInitials() {
        estudianteService = new EstudianteService();
        estudianteService.getFinales(curso.id, new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                finales=(ArrayList<Final>) responseBody;
                ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading_inscripcion_final);
                loadingView.setVisibility(View.INVISIBLE);
                displayFinales();

            }

            @Override
            public void onResponseError(String errorMessage) {
                ProgressBar loadingView = findViewById(R.id.loading_inscripcion_curso);
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
        });
    }


    private void displayFinales() {
        final ListView listView = findViewById(R.id.lista_finales);
        listView.setAdapter(cursosAdapter);
        listView.setEmptyView(findViewById(R.id.emptyElementFinales));
    }
}
