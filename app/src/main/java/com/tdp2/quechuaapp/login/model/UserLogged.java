package com.tdp2.quechuaapp.login.model;

import java.util.Date;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLogged {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("login")
    @Expose
    public String login;
    @SerializedName("firstName")
    @Expose
    public String firstName;
    @SerializedName("lastName")
    @Expose
    public String lastName;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("imageUrl")
    @Expose
    public String imageUrl;
    @SerializedName("activated")
    @Expose
    public Boolean activated;
    @SerializedName("langKey")
    @Expose
    public String langKey;
    @SerializedName("createdBy")
    @Expose
    public String createdBy;
    @SerializedName("createdDate")
    @Expose
    public Date createdDate;
    @SerializedName("lastModifiedBy")
    @Expose
    public String lastModifiedBy;
    @SerializedName("lastModifiedDate")
    @Expose
    public Date lastModifiedDate;
    @SerializedName("authorities")
    @Expose
    public List<String> authorities = null;

}
