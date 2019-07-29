package com.sq.sohel.blooddonor.data.model.api.RateUs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RateUsRequest {

    @Expose
    @SerializedName("comment")
    public String comment;
    @Expose
    @SerializedName("rating")
    public int rating;

    @Expose
    @SerializedName("userId")
    public String userId;

    @Expose
    @SerializedName("applicationId")
    public String applicationId;

}
