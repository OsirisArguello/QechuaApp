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
import com.tdp2.quechuaapp.model.Coloquio;
import com.tdp2.quechuaapp.model.Curso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ColoquiosAdapter extends ArrayAdapter<Coloquio> {

    Curso curso;
    Context context;
    ColoquiosAdapterCallBack adapterCallback;

    public ColoquiosAdapter(@NonNull Context context, @NonNull ArrayList<Coloquio> finales, Curso curso) {
        super(context, 0,  finales);

        this.curso=curso;
        this.context=context;
        try {
            this.adapterCallback = ((ColoquiosAdapterCallBack) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement CursosAdapterCallback.");
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Coloquio coloquio=getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.profesor_final_view, parent, false);
        }

        //Codigo de colores
        Calendar fechaYHoraInicioColoquio = Calendar.getInstance();
        fechaYHoraInicioColoquio.setTime(coloquio.fecha);
        fechaYHoraInicioColoquio.set(Calendar.HOUR_OF_DAY,Integer.parseInt(coloquio.horaInicio.split(":")[0]));
        fechaYHoraInicioColoquio.set(Calendar.MINUTE,Integer.parseInt(coloquio.horaInicio.split(":")[1]));

        Calendar fechaYHoraActual = Calendar.getInstance();

        if(fechaYHoraActual.after(fechaYHoraInicioColoquio)){
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.coloquiosPasados));
        } else {
            fechaYHoraActual.add(Calendar.DAY_OF_MONTH,1);
            if(fechaYHoraActual.before(fechaYHoraInicioColoquio)){
                convertView.setBackgroundColor(getContext().getResources().getColor(R.color.coloquiosFuturos));
            } else {
                convertView.setBackgroundColor(getContext().getResources().getColor(R.color.coloquiosCercanos));
            }
        }

        TextView fechaFinal=convertView.findViewById(R.id.profesor_fecha_final);
        TextView horarioFinal=convertView.findViewById(R.id.profesor_hora_final);
        TextView aulaFinal=convertView.findViewById(R.id.profesor_aula_final);
        TextView inscriptosFinal = convertView.findViewById(R.id.profesor_inscriptos_final);

        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        fechaFinal.setText(sdf.format(coloquio.fecha));
        horarioFinal.setText(coloquio.horaInicio+"-"+coloquio.horaFin);
        aulaFinal.setText(coloquio.sede+"-"+coloquio.aula);
        inscriptosFinal.setText("Inscriptos: " + coloquio.cantInscriptos);

        final Button eliminarColoquioButton = convertView.findViewById(R.id.eliminarFinalProfesor);
        eliminarColoquioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterCallback.eliminarColoquio(coloquio.id, coloquio.cantInscriptos);
            }
        });

        return convertView;
    }
}
