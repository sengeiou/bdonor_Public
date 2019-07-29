package com.sq.sohel.blooddonor.data.model.api.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sq.sohel.blooddonor.data.DataManager;

public class UserRequest {

    @Expose
    @SerializedName("email")
    public String email;

    @Expose
    @SerializedName("userId")
    public String userId;

    @Expose
    @SerializedName("loggedInMode")
    public String loggedInMode;

    @Expose
    @SerializedName("userName")
    public String userName;

    @Expose
    @SerializedName("accessToken")
    public String accessToken;

    @Expose
    @SerializedName("profilePicPath")
    public String profilePicPath;
}
