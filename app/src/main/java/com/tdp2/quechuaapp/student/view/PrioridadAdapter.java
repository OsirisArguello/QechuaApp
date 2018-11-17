package com.tdp2.quechuaapp.student.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tdp2.quechuaapp.R;
import com.tdp2.quechuaapp.model.Prioridad;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

public class PrioridadAdapter extends ArrayAdapter<Prioridad> {


    public PrioridadAdapter(@NonNull Context context, @NonNull ArrayList<Prioridad> listaPrioridad) {
        super(context, 0,  listaPrioridad);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Prioridad prioridad = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.prioridad_view, parent, false);
        }
        // Lookup view for data population

        if (position % 2 == 1) {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.cursosBackground1));
        } else {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.cursosBackground2));
        }

        TextView numero_prioridad = convertView.findViewById(R.id.prioridad_numero);
        TextView fecha_hora = convertView.findViewById(R.id.prioridad_fecha_hora);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        numero_prioridad.setText("N° Prioridad: " + prioridad.numero.toString());
        fecha_hora.setText("Fecha/Hora: "+ sdf.format(prioridad.fecha) + " " + prioridad.hora);

        return convertView;
    }
}
