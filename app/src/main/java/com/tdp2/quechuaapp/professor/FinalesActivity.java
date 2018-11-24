package com.tdp2.quechuaapp.professor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tdp2.quechuaapp.MainActivity;
import com.tdp2.quechuaapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.LinkedHashMap;
import java.util.Map;

import com.tdp2.quechuaapp.model.Coloquio;
import com.tdp2.quechuaapp.model.InscripcionColoquio;
import com.tdp2.quechuaapp.model.Profesor;
import com.tdp2.quechuaapp.networking.Client;
import com.tdp2.quechuaapp.networking.DocenteService;
import com.tdp2.quechuaapp.networking.EstudianteService;
import com.tdp2.quechuaapp.professor.view.FinalesAdapter;
import com.tdp2.quechuaapp.student.view.MisFinalestAdapter;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.widget.ExpandableListView.OnChildClickListener;


public class FinalesActivity extends Activity {

    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> finalesCollection;
    ExpandableListView expListView;
    DocenteService docenteService;
    ArrayList<Coloquio> misFinales;
    Profesor elProf;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_finales_doc);

        docenteService = new DocenteService();
        createGroupList();


    }

    private void createGroupList() {
        docenteService.getMisFinales(new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                misFinales = (ArrayList<Coloquio>) responseBody;
                ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading_mis_finales_doc);
                loadingView.setVisibility(View.INVISIBLE);
                if (misFinales != null) {
                    getProfId();
                }
            }

            @Override
            public void onResponseError(String errorMessage) {
                Log.i("FINALES", "RESPONSER ERROR");
                ProgressBar loadingView = findViewById(R.id.loading_mis_finales_doc);
                loadingView.setVisibility(View.INVISIBLE);
                Toast.makeText(FinalesActivity.this, "No fue posible conectarse al servidor, por favor reintente más tarde",
                        Toast.LENGTH_LONG).show();

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(Toast.LENGTH_LONG); // As I am using LENGTH_LONG in Toast
                            Intent mainActivityIntent = new Intent(FinalesActivity.this, MainActivity.class);
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
                return FinalesActivity.this;
            }
        });
    }

    public void crearVista() {
        expListView = (ExpandableListView) findViewById(R.id.coloquio_list_doc);
        final FinalesAdapter expListAdapter = new FinalesAdapter(
                this, groupList, finalesCollection);
        expListView.setAdapter(expListAdapter);
        expListView.setEmptyView(findViewById(R.id.emptyElementMisColoquios_doc));
        //setGroupIndicatorToRight();

        expListView.setOnChildClickListener(new OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                final String selected = (String) expListAdapter.getChild(
                        groupPosition, childPosition);
                //Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
                //     .show();

                return true;
            }
        });
    }

    public void displayMaterias(){

        groupList = new ArrayList<String>();
        Map<Integer, ArrayList<Coloquio>> map = new HashMap<Integer, ArrayList<Coloquio>>();
        Map<Integer, ArrayList<String>> materias = new HashMap<Integer, ArrayList<String>>();
        Log.i("FINALES", "display");

        for (Coloquio mifinal: misFinales) {
            Integer profId = elProf.id;
            Log.i("FINALES", "bbbbbbb");

            if (mifinal.curso.profesor.id == profId) {
                Log.i("FINALES", "ccccccc");
                ArrayList<Coloquio> unArray = map.get(mifinal.curso.materia.id);
                if (unArray != null) {
                    unArray.add(mifinal);
                } else {
                    ArrayList<Coloquio> nuevoArray = new ArrayList<Coloquio>();
                    nuevoArray.add(mifinal);
                    map.put(mifinal.curso.materia.id, nuevoArray);
                    ArrayList<String> infoMateria = new ArrayList<String>();
                    infoMateria.add(mifinal.curso.materia.codigo);
                    infoMateria.add(mifinal.curso.materia.nombre);
                    infoMateria.add(mifinal.curso.id.toString());
                    materias.put(mifinal.curso.materia.id, infoMateria);
                }
            }
        }
        finalesCollection = new LinkedHashMap<String, List<String>>();
        for (Integer materiaId: map.keySet()) {
            String materiaCompleta = "Materia: " + materias.get(materiaId).get(0) + " - " + materias.get(materiaId).get(1) + '\n' + "Curso: " + materias.get(materiaId).get(2);
            groupList.add(materiaCompleta);
            String[] finales = new String[map.get(materiaId).size()];
            Integer i = 0;
            for (Coloquio micoloquio : map.get(materiaId)) {
                finales[i] = micoloquio.id.toString()+"-"+sdf.format(micoloquio.fecha)+" "+micoloquio.horaInicio+'\n'+"Aula: "+micoloquio.aula;
                i++;
            }
            loadChild(finales);
            finalesCollection.put(materiaCompleta, childList);
        }
        //createCollection();
    }


    private void loadChild(String[] finales) {
        childList = new ArrayList<String>();
        for (String model : finales)
            childList.add(model);
    }

    private void setGroupIndicatorToRight() {
        /* Get the screen width */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        expListView.setIndicatorBounds(width - getDipsFromPixel(35), width
                - getDipsFromPixel(5));
    }

    // Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    public void getProfId() {
        Log.i("FINALES", "profid");

        docenteService.getProfData(new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                Log.i("FINALES", "asasasas");

                elProf = (Profesor) responseBody;
                ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading_mis_finales_doc);
                loadingView.setVisibility(View.INVISIBLE);
                if (elProf != null) {
                    displayMaterias();
                    crearVista();
                }
            }

            @Override
            public void onResponseError(String errorMessage) {
                Log.i("FINALES", "acasdsdsdsddssdsdddsaaa");

                Log.i("FINALES", "RESPONSER ERROR");
                ProgressBar loadingView = findViewById(R.id.loading_mis_finales_doc);
                loadingView.setVisibility(View.INVISIBLE);
                Toast.makeText(FinalesActivity.this, "No fue posible conectarse al servidor, por favor reintente más tarde",
                        Toast.LENGTH_LONG).show();

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(Toast.LENGTH_LONG); // As I am using LENGTH_LONG in Toast
                            Intent mainActivityIntent = new Intent(FinalesActivity.this, MainActivity.class);
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
                return FinalesActivity.this;
            }
        });
    }

}