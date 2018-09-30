package com.tdp2.quechuaapp.professor.view;

import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.tdp2.quechuaapp.R;
import com.tdp2.quechuaapp.model.Alumno;

public class AlumnosAdapter extends ArrayAdapter<Alumno> {

    // TODO: Tiene que haber otra manera de hacer esto
    public Boolean condicionales = false;

    public AlumnosAdapter(@NonNull Context context, @NonNull List<Alumno> listaAlumnos) {
        super(context, 0,  listaAlumnos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Alumno alumno = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.estudiante_view, parent, false);
        }

        if (position % 2 == 1) {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.cursosBackground1));
        } else {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.cursosBackground2));
        }

        TextView textView = convertView.findViewById(R.id.idEstudiante);
        textView.setText(alumno.apellido + ", " + alumno.nombre + " - " + alumno.padron );

        Button button = convertView.findViewById(R.id.inscribirButton);

        button.setVisibility(condicionales ? View.VISIBLE : View.INVISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Si el estudiante es "condicional", inscribir
            }
        });

        return convertView;
    }

}
