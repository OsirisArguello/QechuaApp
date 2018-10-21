package com.tdp2.quechuaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cursada implements Serializable {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("notaCursada")
    @Expose
    public Integer notaCursada;
    @SerializedName("notFinal")
    @Expose
    public Integer notFinal;
    @SerializedName("libro")
    @Expose
    public String libro;
    @SerializedName("folio")
    @Expose
    public String folio;
    @SerializedName("estado")
    @Expose
    public String estado;
    @SerializedName("curso")
    @Expose
    public Curso curso;
    @SerializedName("alumno")
    @Expose
    public Alumno alumno;
    @SerializedName("materia")
    @Expose
    public Materia materia;


}
