package com.sq.sohel.blooddonor.data.model.others;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class CompanyContactRequestCreatedViewModel {
    @Expose
    @SerializedName("label")
    public String Label;
    @Expose
    @SerializedName("name")
    public String Name;
    @Expose
    @SerializedName("phone")
    public String Phone;
    @Expose
    @SerializedName("cell")
    public String Cell;
    @Expose
    @SerializedName("sortOrder")
    public int SortOrder;
    @Expose
    @SerializedName("email")
    public String Email;
    @Expose
    @SerializedName("isUser")
    public boolean IsUser;
    @Expose
    @SerializedName("aspNetUserId")
    public String AspNetUserId;
    @Expose
    @SerializedName("contactTypeId")
    public UUID ContactTypeId;
}

