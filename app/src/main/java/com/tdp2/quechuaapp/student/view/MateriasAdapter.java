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
import com.tdp2.quechuaapp.model.Materia;

import java.util.ArrayList;

public class MateriasAdapter extends ArrayAdapter<Materia> {

    public MateriasAdapter(@NonNull Context context, @NonNull ArrayList<Materia> listaMaterias) {
        super(context, 0,  listaMaterias);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Materia materia = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        }
        // Lookup view for data population
        int colorID = (position % 2 == 1) ? R.color.cursosBackground1 : R.color.cursosBackground2;
        convertView.setBackgroundColor(getContext().getResources().getColor(colorID));

        TextView line1 = convertView.findViewById(android.R.id.text1);
        TextView line2 = convertView.findViewById(android.R.id.text2);

        line1.setText(materia.codigo + " - " + materia.nombre);
        line2.setText("Creditos: "+ materia.creditos);

        return convertView;
    }
}
