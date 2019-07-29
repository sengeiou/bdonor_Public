package com.sq.sohel.blooddonor.data.model.others;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.UUID;

public class UserViewModel
{
    @Expose
    @SerializedName("companyId")
    public UUID CompanyId ;
    @Expose
    @SerializedName("xPTOUserId")
    public UUID XPTOUserId ;
    @Expose
    @SerializedName("userName")
    public String UserName ;
    @Expose
    @SerializedName("passwordHash")
    public String PasswordHash ;
    @Expose
    @SerializedName("emailAddress")
    public String EmailAddress ;
    //public boolean IsActive ;
    //public boolean IsAdmin =false;
    //public boolean ForceResetPassword ;
    @Expose
    @SerializedName("claims")
    public List<UserClaimViewModel> Claims ;
}

