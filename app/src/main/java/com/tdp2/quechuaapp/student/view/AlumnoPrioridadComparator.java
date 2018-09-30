package com.tdp2.quechuaapp.student.view;

import com.tdp2.quechuaapp.model.Alumno;

import java.util.Comparator;

public class AlumnoPrioridadComparator implements Comparator<Alumno> {

    @Override
    public int compare(Alumno alumno, Alumno t1) {
        return alumno.prioridad.compareTo(t1.prioridad);
    }
}
