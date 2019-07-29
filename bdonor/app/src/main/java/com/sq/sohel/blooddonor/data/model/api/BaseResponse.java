package com.sq.sohel.blooddonor.data.model.api;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public abstract class BaseResponse {
    @Expose
    @SerializedName("data")
    public JsonObject Data;

    @Expose
    @SerializedName("message")
    public String Message;

    @Expose
    @SerializedName("result")
    public String Result;
}
