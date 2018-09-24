package com.tdp2.quechuaapp.professor.view;

import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tdp2.quechuaapp.model.Alumno;

public class AlumnosAdapter extends ArrayAdapter<Alumno> {

    public AlumnosAdapter(@NonNull Context context, @NonNull List<Alumno> listaAlumnos) {
        super(context, 0,  listaAlumnos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Alumno alumno = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1,
                    parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView .setText("Nombre: " + alumno.apellido + ", " + alumno.nombre);
        return convertView;
    }

}
