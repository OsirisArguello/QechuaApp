
package com.tdp2.quechuaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    public List<Alumno> est_regulares;
    public List<Alumno> est_condicionales;
}
