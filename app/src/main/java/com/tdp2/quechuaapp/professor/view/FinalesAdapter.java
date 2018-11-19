package com.tdp2.quechuaapp.professor.view;
import java.util.List;
import java.util.Map;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tdp2.quechuaapp.MainActivity;
import com.tdp2.quechuaapp.R;
import com.tdp2.quechuaapp.model.Coloquio;
import com.tdp2.quechuaapp.networking.Client;
import com.tdp2.quechuaapp.networking.EstudianteService;
import com.tdp2.quechuaapp.student.InscripcionColoquioActivity;
import com.tdp2.quechuaapp.student.MisFinalesActivity;

public class FinalesAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private Map<String, List<String>> finalesCollections;
    private List<String> finales;

    public FinalesAdapter(Activity context, List<String> finales,
                              Map<String, List<String>> finalCollections) {
        this.context = context;
        this.finalesCollections = finalCollections;
        this.finales = finales;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return finalesCollections.get(finales.get(groupPosition)).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String unFinal = (String) getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();
        final String coloquioId = unFinal.split("-")[0];

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item_mis_finales, null);
        }
        final Integer coloquioIdInt = Integer.parseInt(coloquioId);

        TextView item = (TextView) convertView.findViewById(R.id.coloquio);

        ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
        delete.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("¿Desea desinscribirse a esta fecha de final?");
                builder.setCancelable(false);
                builder.setPositiveButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                EstudianteService estudianteService = new EstudianteService();
                                estudianteService.desinscribirFinal(coloquioIdInt, new Client() {
                                    @Override
                                    public void onResponseSuccess(Object responseBody) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setMessage("Ha sido desinscripto al final exitosamente.");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("Ok", null);
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }

                                    @Override
                                    public void onResponseError(String errorMessage) {
                                    }


                                    @Override
                                    public Context getContext() {
                                        return context;
                                    }
                                });

                                List<String> child =
                                        finalesCollections.get(finales.get(groupPosition));
                                child.remove(childPosition);
                                notifyDataSetChanged();
                            }
                        });
                builder.setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        item.setText(unFinal.split("-")[1]);
        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        return finalesCollections.get(finales.get(groupPosition)).size();
    }

    public Object getGroup(int groupPosition) {
        return finales.get(groupPosition);
    }

    public int getGroupCount() {
        return finales.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String finalName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item_mis_finales,
                    null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.coloquio);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(finalName);
        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}