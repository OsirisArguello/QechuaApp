package com.tdp2.quechuaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Periodo implements Serializable {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("cuatrimestre")
    @Expose
    public String cuatrimestre;
    @SerializedName("anio")
    @Expose
    public String anio;
}
