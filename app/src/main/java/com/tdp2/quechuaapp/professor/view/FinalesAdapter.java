package com.tdp2.quechuaapp.professor.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tdp2.quechuaapp.R;
import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.model.Final;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FinalesAdapter extends ArrayAdapter<Final> {

    Curso curso;
    Context context;

    public FinalesAdapter(@NonNull Context context, @NonNull ArrayList<Final> finales, Curso curso) {
        super(context, 0,  finales);

        this.curso=curso;
        this.context=context;
        //try {
        //    this.adapterCallback = ((CursosAdapterCallback) context);
        //} catch (ClassCastException e) {
        //    throw new ClassCastException("Activity must implement CursosAdapterCallback.");
        //}
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Final coloquio=getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.profesor_final_view, parent, false);
        }

        // Lookup view for data population

        if (position % 2 == 1) {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.cursosBackground1));
        } else {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.cursosBackground2));
        }

        TextView fechaFinal=convertView.findViewById(R.id.profesor_fecha_final);
        TextView horarioFinal=convertView.findViewById(R.id.profesor_hora_final);
        TextView aulaFinal=convertView.findViewById(R.id.profesor_aula_final);
        TextView inscriptosFinal = convertView.findViewById(R.id.profesor_inscriptos_final);

        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        fechaFinal.setText(sdf.format(coloquio.fecha));
        horarioFinal.setText(coloquio.horaInicio+"-"+coloquio.horaFin);
        aulaFinal.setText(coloquio.sede+"-"+coloquio.aula);

        return convertView;
    }
}
