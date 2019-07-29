package com.sq.sohel.blooddonor.data.model.others;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class  CompanyContactRequestUpdatedViewModel extends  CompanyContactRequestCreatedViewModel{
    @Expose
    @SerializedName("id")
    public UUID Id;
    @Expose
    @SerializedName("status")
    public String Status;
}
