package com.sq.sohel.blooddonor.data.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "requestBlood")
public class RequestBlood {

    @Expose
    @NonNull
    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "Id")
    public String Id;

    @Expose
    @NonNull
    @SerializedName("bloodType")
    @ColumnInfo(name = "BloodType")
    public String BloodType;

    @Expose
    @NonNull
    @SerializedName("requestFor")
    @ColumnInfo(name = "RequestFor")
    public String RequestFor; //Blood or Platelet

    @Expose
    @SerializedName("noOfBags")
    @ColumnInfo(name = "NoOfBags")
    public int NoOfBags;

    @Expose
    @SerializedName("bloodNeedDate")
    @ColumnInfo(name = "BloodNeedDate")
    public String BloodNeedDate;

    @Expose
    @SerializedName("comments")
    @ColumnInfo(name = "Comments")
    public String Comments;

    @Expose
    @SerializedName("contactPerson1")
    @ColumnInfo(name = "ContactPerson1")
    public String ContactPerson1;

    @Expose
    @SerializedName("contactNumber1")
    @ColumnInfo(name = "ContactNumber1")
    public String ContactNumber1;

    @Expose
    @SerializedName("contactPerson2")
    @ColumnInfo(name = "ContactPerson2")
    public String ContactPerson2;

    @Expose
    @SerializedName("contactNumber2")
    @ColumnInfo(name = "ContactNumber2")
    public String ContactNumber2;

    @Expose
    @SerializedName("contactPerson3")
    @ColumnInfo(name = "ContactPerson3")
    public String ContactPerson3;

    @Expose
    @SerializedName("contactNumber3")
    @ColumnInfo(name = "ContactNumber3")
    public String ContactNumber3;

    @Expose
    @SerializedName("longitude")
    @ColumnInfo(name = "Longitude")
    public Double Longitude;

    @Expose
    @SerializedName("latitude")
    @ColumnInfo(name = "Latitude")
    public Double Latitude;

    @Expose
    @SerializedName("address")
    @ColumnInfo(name = "Address")
    public String Address;

    @Expose
    @SerializedName("city")
    @ColumnInfo(name = "City")
    public String City;

    @Expose
    @SerializedName("country")
    @ColumnInfo(name = "Country")
    public String Country;


    @Expose
    @SerializedName("isLocallyAdd")
    @ColumnInfo(name = "IsLocallyAdd")
    public boolean IsLocallyAdd;

    @Expose
    @SerializedName("isSyncWithServer")
    @ColumnInfo(name = "IsSyncWithServer")
    public boolean IsSyncWithServer;

    @Expose
    @SerializedName("addedBy")
    @ColumnInfo(name = "AddedBy")
    public String AddedBy;

    @Expose
    @SerializedName("id_ServerSite")
    @ColumnInfo(name = "Id_ServerSite")
    public String Id_ServerSite;


}
