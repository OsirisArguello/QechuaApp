package com.tdp2.quechuaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Prioridad implements Serializable {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("periodo")
    @Expose
    public Periodo periodo;
    @SerializedName("fecha_habilitacion")
    @Expose
    public String fecha_habilitacion;
}
