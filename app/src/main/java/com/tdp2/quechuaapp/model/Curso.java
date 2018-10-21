
package com.tdp2.quechuaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tdp2.quechuaapp.student.view.AlumnoPrioridadComparator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Curso implements Serializable {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("estado")
    @Expose
    public String estado;
    @SerializedName("vacantes")
    @Expose
    public Integer capacidadCurso;
    @SerializedName("horarios")
    @Expose
    public List<Horario> horarios;
    @SerializedName("profesor")
    @Expose
    public Profesor profesor;
    @SerializedName("periodo")
    @Expose
    public Periodo periodo;
    @SerializedName("materia")
    @Expose
    public Materia materia;

    @SerializedName("inscripciones")
    @Expose
    public List<Inscripcion> inscripciones;

    public List<Alumno> getInscriptosRegulares(){
        List<Alumno> listaAlumnos = new ArrayList<>();
        if(inscripciones!=null) {
            for (Inscripcion inscripcion : inscripciones) {
                if (inscripcion.estado.equals("REGULAR"))
                    listaAlumnos.add(inscripcion.alumno);
            }
        }

        return listaAlumnos;
    }

    public List<Alumno> getInscriptosCondicionales(){
        List<Alumno> listaAlumnos = new ArrayList<>();
        if(inscripciones!=null) {
            for (Inscripcion inscripcion : inscripciones) {
                if (inscripcion.estado.equals("CONDICIONAL"))
                    listaAlumnos.add(inscripcion.alumno);
            }
        }

        Collections.sort(listaAlumnos,new AlumnoPrioridadComparator());

        return listaAlumnos;
    }

    public boolean estaInscripto(Alumno alumno){
        boolean estaInscripto=false;
        if(inscripciones!=null) {
            for (Inscripcion inscripcion : inscripciones) {
                if(alumno.id.equals(inscripcion.alumno.id)){
                    estaInscripto=true;
                }
            }
        }
        return estaInscripto;
    }

    public Integer getVacantes(){

        Integer vacantes = capacidadCurso - getInscriptosRegulares().size();
        return vacantes >= 0 ? vacantes : 0;
    }
}
