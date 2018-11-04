package com.tdp2.quechuaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PeriodoAdministrativo {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("fechaInicio")
    @Expose
    public Date fechaInicio;
    @SerializedName("fechaFin")
    @Expose
    public Date fechaFin;
    @SerializedName("actividad")
    @Expose
    public PeriodoActividad actividad;
}
