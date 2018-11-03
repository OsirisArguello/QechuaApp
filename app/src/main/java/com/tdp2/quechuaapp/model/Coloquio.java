package com.tdp2.quechuaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Coloquio implements Serializable {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("fecha")
    @Expose
    public Date fecha;
    @SerializedName("horaInicio")
    @Expose
    public String horaInicio;
    @SerializedName("horaFin")
    @Expose
    public String horaFin;
    @SerializedName("sede")
    @Expose
    public String sede;
    @SerializedName("aula")
    @Expose
    public String aula;
    @SerializedName("curso")
    @Expose
    public Curso curso;
    @SerializedName("periodo")
    @Expose
    public Periodo periodo;
    @SerializedName("inscripcionesCantidad")
    @Expose
    public Integer inscripcionesCantidad;

    public Boolean inscripto = false;
}