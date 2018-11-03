package com.tdp2.quechuaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InscripcionColoquio  implements Serializable {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("coloquio")
    @Expose
    public Coloquio coloquio;
    @SerializedName("alumno")
    @Expose
    public Alumno alumno;
    @SerializedName("estado")
    @Expose
    public String estado;
}
