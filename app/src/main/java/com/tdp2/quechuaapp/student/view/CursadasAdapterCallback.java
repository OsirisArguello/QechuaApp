package com.tdp2.quechuaapp.student.view;

import android.widget.Button;

import com.tdp2.quechuaapp.model.Cursada;

public interface CursadasAdapterCallback {

    void desinscribirAlumno(Integer idCursada, Button desinscribirseButton);

    void verFinales(Cursada cursada);
}
