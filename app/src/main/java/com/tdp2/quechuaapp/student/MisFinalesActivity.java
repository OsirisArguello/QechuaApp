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

import java.util.ArrayList;
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
    Map<String, List<String>> laptopCollection;
    ExpandableListView expListView;
    EstudianteService estudianteService;
    ArrayList<InscripcionColoquio> misFinales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_finales);

        estudianteService = new EstudianteService();
        createGroupList();

        createCollection();

        expListView = (ExpandableListView) findViewById(R.id.laptop_list);
        final MisFinalestAdapter expListAdapter = new MisFinalestAdapter(
                this, groupList, laptopCollection);
        expListView.setAdapter(expListAdapter);

        //setGroupIndicatorToRight();

        expListView.setOnChildClickListener(new OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                final String selected = (String) expListAdapter.getChild(
                        groupPosition, childPosition);
                Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
                        .show();

                return true;
            }
        });
    }

    private void createGroupList() {
        estudianteService.getMisFinales(new Client() {
            @Override
            public void onResponseSuccess(Object responseBody) {
                misFinales = (ArrayList<InscripcionColoquio>) responseBody;
                Log.i("FINALES", "ENTRA ACAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                ProgressBar loadingView = (ProgressBar) findViewById(R.id.loading_mis_finales);
                loadingView.setVisibility(View.INVISIBLE);
                displayMaterias();
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

    public void displayMaterias(){

        Log.i("FINALES", "HOLAAAAAAAAAAA");

        groupList = new ArrayList<String>();
        /*for (InscripcionColoquio inscripcionFinal: misFinales) {
            groupList.add(inscripcionFinal.coloquio.curso.materia.nombre);
        }*/
        groupList.add("HP"+'\n'+"Pola");
        groupList.add("Dell");
        groupList.add("Lenovo");
        groupList.add("Sony");
        groupList.add("HCL");
        groupList.add("Samsung");
    }

    private void createCollection() {
        // preparing laptops collection(child)
        String[] hpModels = { "HP Pavilion G6-2014TX"+'\n'+"Otra cosa", "ProBook HP 4540",
                "HP Envy 4-1025TX" };
        String[] hclModels = { "HCL S2101", "HCL L2102", "HCL V2002" };
        String[] lenovoModels = { "IdeaPad Z Series", "Essential G Series",
                "ThinkPad X Series", "Ideapad Z Series" };
        String[] sonyModels = { "VAIO E Series", "VAIO Z Series",
                "VAIO S Series", "VAIO YB Series" };
        String[] dellModels = { "Inspiron", "Vostro", "XPS" };
        String[] samsungModels = { "NP Series", "Series 5", "SF Series" };

        laptopCollection = new LinkedHashMap<String, List<String>>();

        for (String laptop : groupList) {
            if (laptop.equals("HP"+'\n'+"Pola")) {
                loadChild(hpModels);
            } else if (laptop.equals("Dell"))
                loadChild(dellModels);
            else if (laptop.equals("Sony"))
                loadChild(sonyModels);
            else if (laptop.equals("HCL"))
                loadChild(hclModels);
            else if (laptop.equals("Samsung"))
                loadChild(samsungModels);
            else
                loadChild(lenovoModels);

            laptopCollection.put(laptop, childList);
        }
    }

    private void loadChild(String[] laptopModels) {
        childList = new ArrayList<String>();
        for (String model : laptopModels)
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