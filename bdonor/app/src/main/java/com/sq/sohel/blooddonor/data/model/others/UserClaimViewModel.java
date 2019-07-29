package com.sq.sohel.blooddonor.data.model.others;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserClaimViewModel
{
    @Expose
    @SerializedName("type")
    public String Type;
    @Expose
    @SerializedName("value")
    public String Value;
}
