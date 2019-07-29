package com.sq.sohel.blooddonor.data.model.api.SignUp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpRequest {

    @Expose
    @SerializedName("name")
    public String name;
    @Expose
    @SerializedName("email")
    public String email;

    @Expose
    @SerializedName("password")
    public String password;

    @Expose
    @SerializedName("access_token")
    public String access_token;

    @Expose
    @SerializedName("contactId")
    public String contactId;

    @Expose
    @SerializedName("userId")
    public String userId;


    @Expose
    @SerializedName("image")
    public byte[] image;

}
