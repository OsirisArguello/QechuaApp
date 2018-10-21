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
import com.tdp2.quechuaapp.model.Cursada;
import com.tdp2.quechuaapp.model.Horario;

import java.util.ArrayList;

public class CursadasAdapter extends ArrayAdapter<Cursada> {

    private CursadasAdapterCallback adapterCallback;

    public CursadasAdapter(@NonNull Context context, @NonNull ArrayList<Cursada> listaCursadas) {
        super(context, 0,  listaCursadas);
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
        TextView idProfTextView = convertView.findViewById(R.id.idProf);
        TextView diaTextView = convertView.findViewById(R.id.dia_horarioCursada);
        TextView horasTextView = convertView.findViewById(R.id.horas_horarioCursada);
        TextView aulaTextView = convertView.findViewById(R.id.aula_horarioCursada);
        final Button boton = convertView.findViewById(R.id.finalButton);

        // Ver si hay mas mensajes segun los otros estados
        boton.setText("Finales");
        //boton.setBackgroundColor(cursada.estado.equals("FINAL_PENDIENTE") ? R.color.lightBlue : R.color.red);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cursada.estado.equals("FINAL_PENDIENTE")) {
                    adapterCallback.verFinales(cursada);
                }
            }
        });

        if(!cursada.estado.equals("FINAL_PENDIENTE")) {
            boton.setVisibility(View.INVISIBLE);
        }



        idMateriaTextView.setText("Materia: "+cursada.curso.materia.codigo.toString()+" - " + cursada.curso.materia.nombre.toString());
        idCursoTextView.setText("Curso: "+cursada.curso.id.toString());
        idProfTextView.setText("Docente: "+cursada.curso.profesor.apellido.toString() + ", " + cursada.curso.profesor.nombre.toString());

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
