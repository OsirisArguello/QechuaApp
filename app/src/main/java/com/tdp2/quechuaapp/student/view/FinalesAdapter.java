package com.tdp2.quechuaapp.student.view;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdp2.quechuaapp.R;
import com.tdp2.quechuaapp.model.Final;

import java.util.ArrayList;

public class FinalesAdapter extends ArrayAdapter<Final> {

    public FinalesAdapter(@NonNull Context context, @NonNull ArrayList<Final> finales) {
        super(context, 0,  finales);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Final aFinal = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.final_view, parent, false);
        }
        // Lookup view for data population

        if (position % 2 == 1) {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.cursosBackground1));
        } else {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.cursosBackground2));
        }

        TextView fechaTextView = convertView.findViewById(R.id.fechaFinal);
        TextView aulaTextView = convertView.findViewById(R.id.aulaFinal);

        final ImageView iconoInscipcion = convertView.findViewById(R.id.inscripcionFinal);
        iconoInscipcion.setImageResource(android.R.drawable.ic_menu_add);
        // si ya esta inscripto, setear: android.R.drawable.ic_menu_delete



        fechaTextView.setText(aFinal.dia + " // "+ aFinal.horaFin + " - " + aFinal.horaFin);
        aulaTextView.setText(aFinal.sede + " - " + aFinal.aula);

        return convertView;
    }

}
