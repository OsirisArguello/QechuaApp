
package com.tdp2.quechuaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Curso {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("estado")
    @Expose
    public String estado;
    @SerializedName("vacantes")
    @Expose
    public Integer vacantes;
    @SerializedName("horarios")
    @Expose
    public List<Horario> horarios;
    @SerializedName("profesor")
    @Expose
    public Profesor profesor;
    @SerializedName("materia")
    @Expose
    public Materia materia;

    @SerializedName("inscripciones")
    @Expose
    public List<Inscripcion> inscripciones;

    public List<Alumno> getInscriptosRegulares(){
        List<Alumno> listaAlumnos = new ArrayList<>();
        for (Inscripcion inscripcion : inscripciones) {
            if(inscripcion.estado.equals("REGULAR"))
                listaAlumnos.add(inscripcion.alumno);
        }

        return listaAlumnos;
    }

    public List<Alumno> getInscriptosCondicionales(){
        List<Alumno> listaAlumnos = new ArrayList<>();
        for (Inscripcion inscripcion : inscripciones) {
            if(inscripcion.estado.equals("CONDICIONAL"))
                listaAlumnos.add(inscripcion.alumno);
        }

        return listaAlumnos;
    }
}
