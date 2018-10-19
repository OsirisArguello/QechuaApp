package com.tdp2.quechuaapp.student.view;

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
import com.tdp2.quechuaapp.model.Cursada;
import com.tdp2.quechuaapp.model.Horario;

import java.util.ArrayList;

public class CursadasAdapter extends ArrayAdapter<Cursada> {

    private CursadasAdapterCallback adapterCallback;
    Alumno alumno;

    public CursadasAdapter(@NonNull Context context, @NonNull ArrayList<Cursada> listaCursadas, Alumno alumno) {
        super(context, 0,  listaCursadas);
        this.alumno=alumno;
        try {
            this.adapterCallback = ((CursadasAdapterCallback) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement CursadasAdapterCallback.");
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Cursada cursada = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cursada_view, parent, false);
        }
        // Lookup view for data population

        if (position % 2 == 1) {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.cursosBackground1));
        } else {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.cursosBackground2));
        }

        TextView idMateriaTextView = convertView.findViewById(R.id.idMateria);
        TextView idCursoTextView = convertView.findViewById(R.id.idCursada);
        TextView diaTextView = convertView.findViewById(R.id.dia_horarioCursada);
        TextView horasTextView = convertView.findViewById(R.id.horas_horarioCursada);
        TextView aulaTextView = convertView.findViewById(R.id.aula_horarioCursada);


        final Button desinscribirseButton = convertView.findViewById(R.id.desinscribirseButton);

        desinscribirseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterCallback.desinscribirAlumno(cursada.id, desinscribirseButton);
            }
        });

        idMateriaTextView.setText("Materia: "+cursada.curso.materia.codigo.toString()+" - " + cursada.curso.materia.nombre.toString());
        idCursoTextView.setText("Curso: "+cursada.curso.id.toString());

        StringBuilder diaString=new StringBuilder();
        StringBuilder horasString=new StringBuilder();
        StringBuilder aulaString=new StringBuilder();
        Integer cantHorarios=1;
        if (cursada.curso.horarios != null) {
            for (Horario horario : cursada.curso.horarios) {
                diaString.append(horario.dia);
                horasString.append(horario.horaInicio + "-" + horario.horaFin);
                aulaString.append(horario.aula);
                if (cantHorarios < cursada.curso.horarios.size()) {
                    diaString.append("\n");
                    horasString.append("\n");
                    aulaString.append("\n");
                }
                cantHorarios++;
            }
        }
        diaTextView.setText(diaString.toString());
        horasTextView.setText(horasString.toString());
        aulaTextView.setText(aulaString.toString());

        return convertView;
    }
}
