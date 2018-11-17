package com.tdp2.quechuaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Prioridad implements Serializable {
    @SerializedName("numero")
    @Expose
    public Integer numero;
    @SerializedName("fecha")
    @Expose
    public Date fecha;
    @SerializedName("hora")
    @Expose
    public String hora;
}
