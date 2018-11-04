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
import com.tdp2.quechuaapp.model.InscripcionColoquio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MisFinalesAdapter2 extends ArrayAdapter<InscripcionColoquio> {

    public MisFinalesAdapter2(@NonNull Context context, @NonNull ArrayList<InscripcionColoquio> finales) {
        super(context, 0,  finales);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final InscripcionColoquio aInscripcionColoquio = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.mis_finales_view, parent, false);
        }

        if (position % 2 == 1) {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.cursosBackground1));
        } else {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.cursosBackground2));
        }

        TextView materiaTextView = convertView.findViewById(R.id.materiaMiFinal);
        TextView cursoTextView = convertView.findViewById(R.id.cursoMiFinal);
        TextView separadorTextView = convertView.findViewById(R.id.separadorMiFinal);
        TextView fechaTextView = convertView.findViewById(R.id.fechaMiFinal);
        TextView aulaTextView = convertView.findViewById(R.id.aulaMiFinal);

        final ImageView iconoInscipcion = convertView.findViewById(R.id.inscripcionMiFinal);
        iconoInscipcion.setImageResource(aInscripcionColoquio.coloquio.inscripto ? android.R.drawable.ic_delete : android.R.drawable.ic_input_add);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        materiaTextView.setText("Materia: "+aInscripcionColoquio.coloquio.curso.materia.codigo+" - "+aInscripcionColoquio.coloquio.curso.materia.nombre);
        cursoTextView.setText("Curso: "+aInscripcionColoquio.coloquio.curso.id);
        separadorTextView.setText("_______________________________________");
        if(aInscripcionColoquio.id==2){
            materiaTextView.setVisibility(View.INVISIBLE);
            cursoTextView.setVisibility(View.INVISIBLE);
            separadorTextView.setVisibility(View.INVISIBLE);
        }
        fechaTextView.setText(sdf.format(aInscripcionColoquio.coloquio.fecha) + " // "+ aInscripcionColoquio.coloquio.horaInicio + " - " + aInscripcionColoquio.coloquio.horaFin);
        aulaTextView.setText(aInscripcionColoquio.coloquio.sede + " - " + aInscripcionColoquio.coloquio.aula);

        return convertView;
    }

}
