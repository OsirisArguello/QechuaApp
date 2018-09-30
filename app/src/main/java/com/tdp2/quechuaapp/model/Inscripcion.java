
package com.tdp2.quechuaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Inscripcion implements Serializable{

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("estado")
    @Expose
    public String estado;
    @SerializedName("alumno")
    @Expose
    public Alumno alumno;
    @SerializedName("curso")
    @Expose
    public Curso curso;

}
