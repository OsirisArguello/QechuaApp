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

import java.util.ArrayList;

public class CursosDocenteAdapter extends ArrayAdapter<Curso> {

    private CursosDocenteAddFinalAdapterCallback adapterCallback;
    private Curso curso;
    private boolean mostrado;

    public CursosDocenteAdapter(@NonNull Context context, @NonNull ArrayList<Curso> listaCursos, Curso cursoEnCuestion) {
        super(context, 0, listaCursos);
        this.mostrado = false;
        this.curso = cursoEnCuestion;
        try {
            this.adapterCallback = ((CursosDocenteAddFinalAdapterCallback) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement CursosAdapterCallback.");
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (!this.mostrado) {
            final Curso curso = this.curso;

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.add_curso_finales_view, parent, false);
            }
            // Lookup view for data population

            if (position % 2 == 1) {
                convertView.setBackgroundColor(getContext().getResources().getColor(R.color.cursosBackground1));
            } else {
                convertView.setBackgroundColor(getContext().getResources().getColor(R.color.cursosBackground2));
            }

            TextView idMateriaFinalTextView = convertView.findViewById(R.id.idMateriaFinal);
            TextView idCursoFinalTextView = convertView.findViewById(R.id.idCursoFinal);
            TextView idCuatrimestreFinalTextView = convertView.findViewById(R.id.idCuatrimestreFinal);

            final Button buttomAddFinal = convertView.findViewById(R.id.idButtomAddFinal);

            buttomAddFinal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapterCallback.agregarFecha(curso.id, buttomAddFinal);
                }
            });


            idMateriaFinalTextView.setText("Materia: " + curso.materia.codigo.toString() + " - " + curso.materia.nombre.toString());
            idCursoFinalTextView.setText("Curso: " + curso.id);
            idCuatrimestreFinalTextView.setText(curso.periodo.cuatrimestre + "°" + " Cuatrimestre " + curso.periodo.anio);
            this.mostrado = true;
            return convertView;
        }
        return convertView;
    }


}
