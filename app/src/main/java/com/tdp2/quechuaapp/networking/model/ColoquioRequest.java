package com.tdp2.quechuaapp.networking.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.model.Periodo;

import java.util.Date;

public class ColoquioRequest {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("fecha")
    @Expose
    public String fecha;
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
    @SerializedName("estado")
    @Expose
    public String estado;
}
