package com.tdp2.quechuaapp.student.view;

import com.tdp2.quechuaapp.model.Alumno;
import com.tdp2.quechuaapp.model.Inscripcion;

import java.util.Comparator;

public class InscripcionPrioridadComparator implements Comparator<Inscripcion> {

    @Override
    public int compare(Inscripcion inscripcion, Inscripcion t1) {
        return inscripcion.alumno.prioridad.compareTo(t1.alumno.prioridad);
    }
}
