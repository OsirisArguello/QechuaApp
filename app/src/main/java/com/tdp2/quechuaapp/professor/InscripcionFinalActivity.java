package com.tdp2.quechuaapp.professor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tdp2.quechuaapp.MainActivity;
import com.tdp2.quechuaapp.R;
import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.networking.Client;
import com.tdp2.quechuaapp.networking.DocenteService;
import com.tdp2.quechuaapp.professor.view.CursosDocenteAdapter;
import com.tdp2.quechuaapp.professor.view.CursosDocenteAdapterCallback;

import java.util.ArrayList;

public class InscripcionFinalActivity extends AppCompatActivity implements CursosDocenteAdapterCallback {

    public Curso curso;
    ArrayList<Curso> cursos;
    private ViewPager viewPager;
    private DocenteService docenteService;
    CursosDocenteAdapter cursosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor_addfinal);
        setupInitials();
    }

    private void setupInitials() {
        cursos=new ArrayList<>();
        ProgressBar loadingView = findViewById(R.id.loading_addfinal);
        loadingView.bringToFront();
        loadingView.setVisibility(View.VISIBLE);

        docenteService = new DocenteService();
        docenteService.getCursos(new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                cursos=(ArrayList<Curso>) responseBody;
                ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading_addfinal);
                loadingView.setVisibility(View.INVISIBLE);
                displayCursos();
            }

            @Override
            public void onResponseError(String errorMessage) {
                ProgressBar loadingView = findViewById(R.id.loading_addfinal);
                loadingView.setVisibility(View.INVISIBLE);

                Toast.makeText(InscripcionFinalActivity.this, "No fue posible conectarse al servidor, por favor reintente más tarde",
                        Toast.LENGTH_LONG).show();

                Thread thread = new Thread() {
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

    private void displayCursos() {
        final ListView cursosListView = findViewById(R.id.lista_cursos_final);
        cursosAdapter = new CursosDocenteAdapter(this, cursos);
        cursosListView.setAdapter(cursosAdapter);
        cursosListView.setEmptyView(findViewById(R.id.emptyElement_final));
    }

    @Override
    public void agregarFecha(final Integer idCurso, final Button inscribirseButton) {

        ProgressBar loadingView = findViewById(R.id.loading_inscripcion_curso);
        loadingView.setVisibility(View.VISIBLE);
        loadingView.bringToFront();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);



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
        cursosAdapter.notifyDataSetChanged();
    }
}

