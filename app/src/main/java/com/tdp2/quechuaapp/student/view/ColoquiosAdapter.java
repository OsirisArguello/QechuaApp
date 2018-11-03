package com.tdp2.quechuaapp.student.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdp2.quechuaapp.R;
import com.tdp2.quechuaapp.model.Coloquio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ColoquiosAdapter extends ArrayAdapter<Coloquio> {

    public ColoquiosAdapter(@NonNull Context context, @NonNull ArrayList<Coloquio> finales) {
        super(context, 0,  finales);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Coloquio aColoquio = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.final_view, parent, false);
        }

        if (position % 2 == 1) {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.cursosBackground1));
        } else {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.cursosBackground2));
        }

        TextView fechaTextView = convertView.findViewById(R.id.fechaFinal);
        TextView aulaTextView = convertView.findViewById(R.id.aulaFinal);

        final ImageView iconoInscipcion = convertView.findViewById(R.id.inscripcionFinal);
        iconoInscipcion.setImageResource(aColoquio.inscripto ? android.R.drawable.ic_delete : android.R.drawable.ic_input_add);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        fechaTextView.setText(sdf.format(aColoquio.fecha) + " // "+ aColoquio.horaInicio + " - " + aColoquio.horaFin);
        aulaTextView.setText(aColoquio.sede + " - " + aColoquio.aula);

        return convertView;
    }

}
