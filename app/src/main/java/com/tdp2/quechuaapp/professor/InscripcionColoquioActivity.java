package com.tdp2.quechuaapp.professor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tdp2.quechuaapp.MainActivity;
import com.tdp2.quechuaapp.R;
import com.tdp2.quechuaapp.model.Coloquio;
import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.networking.Client;
import com.tdp2.quechuaapp.networking.DocenteService;
import com.tdp2.quechuaapp.networking.model.ColoquioRequest;
import com.tdp2.quechuaapp.professor.view.ColoquiosAdapter;
import com.tdp2.quechuaapp.professor.view.ColoquiosAdapterCallBack;
import com.tdp2.quechuaapp.utils.view.DialogBuilder;

import java.util.ArrayList;

public class InscripcionColoquioActivity extends AppCompatActivity implements ColoquiosAdapterCallBack {

    public Curso curso;
    private DocenteService docenteService;
    ArrayList<Coloquio> coloquios;
    private ColoquiosAdapter coloquiosAdapter;

    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor_curso_finales);
        curso = (Curso) getIntent().getSerializableExtra("curso");
        setupInitials();
    }

    private void setupInitials() {
        coloquios=new ArrayList<>();

        docenteService = new DocenteService();
        getCurso();
        getColoquios();

        ImageView agregarColoquio=findViewById(R.id.agregarNuevoColoquio);
        agregarColoquio.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(coloquios.size()>=5){
                    DialogBuilder.showAlert("No se pueden asignar más de 5 fechas de final a un curso por cuatrimestre.","Nuevo Final",InscripcionColoquioActivity.this);
                    return;
                }
                Intent nuevoFinalIntent = new Intent(InscripcionColoquioActivity.this, NuevoColoquioActivity.class);
                nuevoFinalIntent.putExtra("curso",curso);
                startActivityForResult(nuevoFinalIntent,REQUEST_CODE);
            }
        });

    }

    private void getColoquios() {
        ProgressBar loadingView = findViewById(R.id.loading_profesor_cursos_finales);
        loadingView.setVisibility(View.VISIBLE);
        loadingView.bringToFront();
        docenteService.getColoquios(curso.id, new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                ProgressBar loadingView = findViewById(R.id.loading_profesor_cursos_finales);
                loadingView.setVisibility(View.INVISIBLE);
                coloquios = (ArrayList<Coloquio>) responseBody;
                displayFinales();

            }

            @Override
            public void onResponseError(String errorMessage) {
                ProgressBar loadingView = findViewById(R.id.loading_profesor_cursos_finales);
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

    private void getCurso() {
        ProgressBar loadingView = findViewById(R.id.loading_profesor_cursos_finales);
        loadingView.bringToFront();
        loadingView.setVisibility(View.VISIBLE);
        docenteService.getCurso(curso.id, new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                ProgressBar loadingView = findViewById(R.id.loading_profesor_cursos_finales);
                loadingView.setVisibility(View.INVISIBLE);

                curso = (Curso) responseBody;
                TextView materia=findViewById(R.id.profesor_cursos_finales_materia);
                materia.setText("Materia: "+curso.materia.codigo+"-"+curso.materia.nombre);
                TextView cursoText=findViewById(R.id.profesor_cursos_finales_curso);
                cursoText.setText("Curso: "+curso.id);
                TextView cuatrimestre=findViewById(R.id.profesor_cursos_finales_cuatrimestre);
                cuatrimestre.setText("Cuatrimestre: "+curso.periodo.cuatrimestre+" "+curso.periodo.anio);
            }

            @Override
            public void onResponseError(String errorMessage) {
                ProgressBar loadingView = findViewById(R.id.loading_profesor_cursos_finales);
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

    private void displayFinales() {
        final ListView finalesListView = findViewById(R.id.lista_profesor_finales);
        coloquiosAdapter = new ColoquiosAdapter(this, coloquios, curso);
        finalesListView.setAdapter(coloquiosAdapter);
        finalesListView.setEmptyView(findViewById(R.id.emptyList_profesor_finales));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getColoquios();
        coloquiosAdapter.notifyDataSetChanged();
        displayFinales();

    }

    @Override
    public void eliminarColoquio(final Integer idColoquio, Integer cantInscriptos) {

        String messageToDisplay=String.format(getResources().getString(R.string.confirmacionEliminarColoquio),cantInscriptos);
        showConfirmationAlert(InscripcionColoquioActivity.this, "Eliminación de Coloquio", messageToDisplay, "Eliminar Coloquio","Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        eliminarColoquioConfirmado(idColoquio);
                    }
                });


    }

    private void eliminarColoquioConfirmado(Integer idColoquio){
        ProgressBar loadingView = findViewById(R.id.loading_profesor_cursos_finales);
        loadingView.bringToFront();
        loadingView.setVisibility(View.VISIBLE);
        ColoquioRequest coloquio = new ColoquioRequest();
        coloquio.id=idColoquio;
        docenteService.eliminarColoquio(coloquio, new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                ProgressBar loadingView = findViewById(R.id.loading_profesor_cursos_finales);
                loadingView.setVisibility(View.INVISIBLE);

                String messageToDisplay=String.format(getResources().getString(R.string.eliminacionColoquioOk));

                DialogBuilder.showAlert(messageToDisplay,"Eliminación Satisfactoria",InscripcionColoquioActivity.this);
                getColoquios();
                coloquiosAdapter.notifyDataSetChanged();
                displayFinales();
            }

            @Override
            public void onResponseError(String errorMessage) {
                ProgressBar loadingView = findViewById(R.id.loading_profesor_cursos_finales);
                loadingView.setVisibility(View.INVISIBLE);

                Toast.makeText(InscripcionColoquioActivity.this, errorMessage, Toast.LENGTH_LONG).show();

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

    public void showConfirmationAlert(Context context, String title, String messageToDisplay, String positiveMessage, String negativeMessage, DialogInterface.OnClickListener positiveListener){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(messageToDisplay);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, positiveMessage, positiveListener);


        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, negativeMessage,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}

