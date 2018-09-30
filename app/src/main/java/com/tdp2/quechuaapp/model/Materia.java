
package com.tdp2.quechuaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Materia implements Serializable{

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("nombre")
    @Expose
    public String nombre;
    @SerializedName("codigo")
    @Expose
    public String codigo;
    @SerializedName("creditos")
    @Expose
    public Integer creditos;
    @SerializedName("departamento")
    @Expose
    public Departamento departamento;

}
