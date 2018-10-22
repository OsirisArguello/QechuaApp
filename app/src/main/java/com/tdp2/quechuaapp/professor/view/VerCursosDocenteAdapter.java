package com.tdp2.quechuaapp.professor.view;

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
import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.model.Horario;

import java.util.ArrayList;

public class VerCursosDocenteAdapter extends ArrayAdapter<Curso> {

    private VerCursosDocenteAdapterCallback adapterCallback;

    public VerCursosDocenteAdapter(@NonNull Context context, @NonNull ArrayList<Curso> listaCursos) {
        super(context, 0, listaCursos);
        try {
            this.adapterCallback = ((VerCursosDocenteAdapterCallback) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement VerCursosDocenteAdapterCallback.");
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final Curso curso = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ver_cursos_docente_view, parent, false);
        }
        // Lookup view for data population

        if (position % 2 == 1) {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.cursosBackground1));
        } else {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.cursosBackground2));
        }

        TextView idMateriaFinalTextView = convertView.findViewById(R.id.idMateriaDocente);
        TextView idCursoFinalTextView = convertView.findViewById(R.id.idCursoDocente);
        TextView diaTextView = convertView.findViewById(R.id.dia_horario_docente);
        TextView horasTextView = convertView.findViewById(R.id.horas_horario_docente);
        TextView aulaTextView = convertView.findViewById(R.id.aula_horario_docente);

        StringBuilder diaString=new StringBuilder();
        StringBuilder horasString=new StringBuilder();
        StringBuilder aulaString=new StringBuilder();
        Integer cantHorarios=1;

        for (Horario horario : curso.horarios) {
            diaString.append(horario.dia);
            horasString.append(horario.horaInicio+"-"+horario.horaFin);
            aulaString.append(horario.sede+"-"+horario.aula);
            if(cantHorarios<curso.horarios.size()){
                diaString.append("\n");
                horasString.append("\n");
                aulaString.append("\n");
            }
            cantHorarios++;
        }

        diaTextView.setText(diaString.toString());
        horasTextView.setText(horasString.toString());
        aulaTextView.setText(aulaString.toString());


        final Button buttomVerFechasFinal = convertView.findViewById(R.id.idButtomVerFechasFinal);

        buttomVerFechasFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterCallback.verFechasFinal(curso.id, buttomVerFechasFinal);
            }
        });

        final Button buttomVerInscriptosCursos = convertView.findViewById(R.id.idButtomVerCurso);

        buttomVerInscriptosCursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterCallback.verInscriptosCursos(curso.id, buttomVerInscriptosCursos);
            }
        });


        idMateriaFinalTextView.setText("Materia: " + curso.materia.codigo.toString() + " - " + curso.materia.nombre.toString());
        idCursoFinalTextView.setText("Curso: " + curso.id);
        return convertView;

    }


}
