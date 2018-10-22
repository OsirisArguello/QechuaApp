package com.tdp2.quechuaapp.professor.view;

import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.tdp2.quechuaapp.R;
import com.tdp2.quechuaapp.model.Alumno;
import com.tdp2.quechuaapp.model.Inscripcion;

public class ListadoInscriptosAdapter extends ArrayAdapter<Inscripcion> {

    private ListadoInscriptosAdapterCallback adapterCallback;
    public Boolean condicionales = false;

    public ListadoInscriptosAdapter(@NonNull Context context, @NonNull List<Inscripcion> listaAlumnos) {
        super(context, 0,  listaAlumnos);
        try{
            this.adapterCallback = (ListadoInscriptosAdapterCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement CursosAdapterCallback.");
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Inscripcion inscripcion = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.estudiante_view, parent, false);
        }

        if (position % 2 == 1) {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.cursosBackground1));
        } else {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.cursosBackground2));
        }

        TextView textView = convertView.findViewById(R.id.idEstudiante);
        textView.setText(inscripcion.alumno.apellido + ", " + inscripcion.alumno.nombre + " - " + inscripcion.alumno.padron );

        Button inscribirButton = convertView.findViewById(R.id.inscribirButton);
        Button rechazarButton = convertView.findViewById(R.id.rechazarButton);

        inscribirButton.setVisibility(condicionales ? View.VISIBLE : View.INVISIBLE);
        rechazarButton.setVisibility(condicionales ? View.VISIBLE : View.INVISIBLE);
        if(condicionales){
            inscribirButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapterCallback.aceptar(inscripcion.id);
                }
            });
            rechazarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapterCallback.rechazar(inscripcion.id);
                }
            });
        }


        return convertView;
    }

}
