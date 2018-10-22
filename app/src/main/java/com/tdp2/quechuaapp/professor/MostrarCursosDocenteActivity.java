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
import com.tdp2.quechuaapp.professor.view.CursosDocenteAddFinalAdapterCallback;
import com.tdp2.quechuaapp.professor.view.VerCursosDocenteAdapter;
import com.tdp2.quechuaapp.professor.view.VerCursosDocenteAdapterCallback;

import java.util.ArrayList;

public class MostrarCursosDocenteActivity extends AppCompatActivity implements VerCursosDocenteAdapterCallback {

    public Curso curso;
    ArrayList<Curso> cursos;
    private ViewPager viewPager;
    private DocenteService docenteService;
    VerCursosDocenteAdapter cursosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor_vercursos);
        setupInitials();
    }

    private void setupInitials() {
        cursos=new ArrayList<>();
        ProgressBar loadingView = findViewById(R.id.loading_vercursos);
        loadingView.bringToFront();
        loadingView.setVisibility(View.VISIBLE);

        docenteService = new DocenteService();
        docenteService.getCursos(new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                cursos=(ArrayList<Curso>) responseBody;
                ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading_vercursos);
                loadingView.setVisibility(View.INVISIBLE);
                displayCursos();
            }

            @Override
            public void onResponseError(String errorMessage) {
                ProgressBar loadingView = findViewById(R.id.loading_vercursos);
                loadingView.setVisibility(View.INVISIBLE);

                Toast.makeText(MostrarCursosDocenteActivity.this, "No fue posible conectarse al servidor, por favor reintente más tarde",
                        Toast.LENGTH_LONG).show();

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(Toast.LENGTH_LONG); // As I am using LENGTH_LONG in Toast
                            Intent mainActivityIntent = new Intent(MostrarCursosDocenteActivity.this, MainActivity.class);
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
                return MostrarCursosDocenteActivity.this;
            }
        });
    }

    private void displayCursos() {
        final ListView cursosListView = findViewById(R.id.lista_cursos_final_docente);
        cursosAdapter = new VerCursosDocenteAdapter(this, cursos);
        cursosListView.setAdapter(cursosAdapter);
        cursosListView.setEmptyView(findViewById(R.id.emptyElement_final_cursos_docente));
    }

    @Override
    public void verFechasFinal(final Integer idCurso, final Button verFechasFinalButtom) {
        Intent inscripcionFinalIntent = new Intent(MostrarCursosDocenteActivity.this, InscripcionFinalActivity.class);
        Curso curso=new Curso();
        curso.id=idCurso;
        inscripcionFinalIntent.putExtra("curso",curso);
        startActivity(inscripcionFinalIntent);
    }

    @Override
    public void verInscriptosCursos(final Integer idCurso, final Button verFechasFinalButtom) {
        Intent detalleCursoIntent = new Intent(MostrarCursosDocenteActivity.this, DetalleCursoActivity.class);
        Curso curso=new Curso();
        curso.id=idCurso;
        detalleCursoIntent.putExtra("curso",curso);
        startActivity(detalleCursoIntent);
    }

    private void showAlert(String messageToDisplay, String title) {
        AlertDialog alertDialog = new AlertDialog.Builder(MostrarCursosDocenteActivity.this).create();
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
