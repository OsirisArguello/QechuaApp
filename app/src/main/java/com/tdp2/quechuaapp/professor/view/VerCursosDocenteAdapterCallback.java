package com.tdp2.quechuaapp.professor.view;

import android.widget.Button;

public interface VerCursosDocenteAdapterCallback {
    void verFechasFinal(Integer idCurso, Button verFechasFinal);
    void verInscriptosCursos(Integer idCurso, Button verInscriptosCursos);
}
