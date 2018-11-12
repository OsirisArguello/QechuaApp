package com.tdp2.quechuaapp.student.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tdp2.quechuaapp.R;
import com.tdp2.quechuaapp.login.model.UserSessionManager;
import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.model.Horario;
import com.tdp2.quechuaapp.model.Inscripcion;
import com.tdp2.quechuaapp.model.PeriodoActividad;

import java.util.ArrayList;

public class InscripcionActivaAdapter extends ArrayAdapter<Inscripcion> {

    private CursosAdapterCallback adapterCallback;
    private Boolean desinscripcionActiva;

    public InscripcionActivaAdapter(@NonNull Context context, @NonNull ArrayList<Inscripcion> inscripciones) {
        super(context, 0,  inscripciones);
        try {
            this.adapterCallback = ((CursosAdapterCallback) context);

            UserSessionManager userSessionManager = new UserSessionManager(context);
            ArrayList<PeriodoActividad> actividades = userSessionManager.getActividadValida();
            desinscripcionActiva = actividades.contains(PeriodoActividad.DESINSCRIPCION_CURSADA);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement CursosAdapterCallback.");
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Inscripcion inscripcion = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.inscripcion_view, parent, false);
        }
        // Lookup view for data population

        if (position % 2 == 1) {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.cursosBackground1));
        } else {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.cursosBackground2));
        }

        TextView cursoTextView = convertView.findViewById(R.id.datosCurso);
        TextView docenteTextView = convertView.findViewById(R.id.nombreDocente);
        TextView diaTextView = convertView.findViewById(R.id.dia_horario);
        TextView horasTextView = convertView.findViewById(R.id.horas_horario);
        TextView aulaTextView = convertView.findViewById(R.id.aula_horario);
        TextView estadoTextView = convertView.findViewById(R.id.estadoInscripcion);

        AppCompatButton button = convertView.findViewById(R.id.desinscripcionButton);
        button.setVisibility(desinscripcionActiva ? View.VISIBLE : View.INVISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterCallback.desinscribirAlumno(inscripcion.id);
            }
        });


        Curso curso = inscripcion.curso;
        cursoTextView.setText(curso.materia.codigo + " - " + curso.materia.nombre + "\nCurso: " + curso.id);
        docenteTextView.setText("Docente: "+curso.profesor.apellido+", "+curso.profesor.nombre);

        StringBuilder diaString=new StringBuilder();
        StringBuilder horasString=new StringBuilder();
        StringBuilder aulaString=new StringBuilder();
        Integer cantHorarios=1;

        for (Horario horario : curso.horarios) {
            diaString.append(horario.dia);
            horasString.append(horario.horaInicio+"-"+horario.horaFin);
            aulaString.append(horario.aula);
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

        estadoTextView.setText("Condicion " + inscripcion.estado.toLowerCase());

        return convertView;
    }
}
