package com.tdp2.quechuaapp.student;

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

import com.tdp2.quechuaapp.model.InscripcionColoquio;
import com.tdp2.quechuaapp.networking.Client;
import com.tdp2.quechuaapp.networking.EstudianteService;
import com.tdp2.quechuaapp.student.view.MisFinalestAdapter;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.widget.ExpandableListView.OnChildClickListener;


public class MisFinalesActivity extends Activity {

    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> finalesCollection;
    ExpandableListView expListView;
    EstudianteService estudianteService;
    ArrayList<InscripcionColoquio> misFinales;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_finales);

        estudianteService = new EstudianteService();
        createGroupList();


    }

    private void createGroupList() {
        estudianteService.getMisFinales(new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                misFinales = (ArrayList<InscripcionColoquio>) responseBody;
                ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading_mis_finales);
                loadingView.setVisibility(View.INVISIBLE);
                if (misFinales != null) {
                    displayMaterias();
                    crearVista();
                }
            }

            @Override
            public void onResponseError(String errorMessage) {
                Log.i("FINALES", "RESPONSER ERROR");
                ProgressBar loadingView = findViewById(R.id.loading_mis_finales);
                loadingView.setVisibility(View.INVISIBLE);
                Toast.makeText(MisFinalesActivity.this, "No fue posible conectarse al servidor, por favor reintente más tarde",
                        Toast.LENGTH_LONG).show();

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(Toast.LENGTH_LONG); // As I am using LENGTH_LONG in Toast
                            Intent mainActivityIntent = new Intent(MisFinalesActivity.this, MainActivity.class);
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
                return MisFinalesActivity.this;
            }
        });
    }

    public void crearVista() {
        expListView = (ExpandableListView) findViewById(R.id.coloquio_list);
        final MisFinalestAdapter expListAdapter = new MisFinalestAdapter(
                this, groupList, finalesCollection);
        expListView.setAdapter(expListAdapter);

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
        Map<Integer, ArrayList<InscripcionColoquio>> map = new HashMap<Integer, ArrayList<InscripcionColoquio>>();
        Map<Integer, ArrayList<String>> materias = new HashMap<Integer, ArrayList<String>>();
        for (InscripcionColoquio inscripcionFinal: misFinales) {
            ArrayList<InscripcionColoquio> unArray = map.get(inscripcionFinal.coloquio.curso.materia.id);
            if (unArray != null) {
                unArray.add(inscripcionFinal);
            } else {
                ArrayList<InscripcionColoquio> nuevoArray = new ArrayList<InscripcionColoquio>();
                nuevoArray.add(inscripcionFinal);
                map.put(inscripcionFinal.coloquio.curso.materia.id, nuevoArray);
                ArrayList<String> infoMateria = new ArrayList<String>();
                infoMateria.add(inscripcionFinal.coloquio.curso.materia.codigo);
                infoMateria.add(inscripcionFinal.coloquio.curso.materia.nombre);
                infoMateria.add(inscripcionFinal.coloquio.curso.id.toString());
                materias.put(inscripcionFinal.coloquio.curso.materia.id, infoMateria);
            }
        }
        finalesCollection = new LinkedHashMap<String, List<String>>();
        for (Integer materiaId: map.keySet()) {
            String materiaCompleta = "Materia: " + materias.get(materiaId).get(0) + " - " + materias.get(materiaId).get(1) + '\n' + "Curso: " + materias.get(materiaId).get(2);
            groupList.add(materiaCompleta);
            String[] finales = new String[map.get(materiaId).size()];
            Integer i = 0;
            for (InscripcionColoquio inscripcionColoquio : map.get(materiaId)) {
                finales[i] = inscripcionColoquio.coloquio.id.toString()+"-"+sdf.format(inscripcionColoquio.coloquio.fecha)+" "+inscripcionColoquio.coloquio.horaInicio+'\n'+"Aula: "+inscripcionColoquio.coloquio.aula;
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

}