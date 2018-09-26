
package com.tdp2.quechuaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Alumno {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("nombre")
    @Expose
    public String nombre;
    @SerializedName("apellido")
    @Expose
    public String apellido;
    @SerializedName("padron")
    @Expose
    public String padron;

}
