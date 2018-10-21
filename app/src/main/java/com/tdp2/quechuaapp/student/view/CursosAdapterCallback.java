package com.tdp2.quechuaapp.student.view;

import android.widget.Button;

public interface CursosAdapterCallback {

    void inscribirAlumno(Integer idCurso);

    void desinscribirAlumno(Integer idInscripcion);
}
