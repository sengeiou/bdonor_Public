package com.sq.sohel.blooddonor.data.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sq.sohel.blooddonor.data.remote.ApiHeader;

import java.util.Date;

@Entity(tableName = "donors", indices={@Index(value="Email", unique=true)})
public class Donor {

    @Expose
    @NonNull
    @PrimaryKey()
    @SerializedName("id")
    public String Id;

    @Expose
    @SerializedName("name")
    @ColumnInfo(name = "Name")
    public String Name;

    @Expose
    @SerializedName("email")
    @ColumnInfo(name = "Email")
    public String Email;

    @Expose
    @SerializedName("gender")
    @ColumnInfo(name = "Gender")
    public String Gender;

    @Expose
    @SerializedName("contactNumber")
    @ColumnInfo(name = "ContactNumber")
    public String ContactNumber;

    @Expose
    @SerializedName("longitude")
    @ColumnInfo(name = "Longitude")
    public Double Longitude;

    @Expose
    @SerializedName("latitude")
    @ColumnInfo(name = "Latitude")
    public Double Latitude;

    @Expose
    @SerializedName("bloodType")
    @ColumnInfo(name = "BloodType")
    public String BloodType;

    @Expose
    @SerializedName("weightLBS")
    @ColumnInfo(name = "WeightLBS")
    public Integer WeightLBS;

    @Expose
    @SerializedName("heightIN")
    @ColumnInfo(name = "HeightIN")
    public Integer HeightIN;

    @Expose
    @SerializedName("lastDonationDate")
    @ColumnInfo(name = "LastDonationDate")
    public String LastDonationDate;

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
    @SerializedName("dob")
    @ColumnInfo(name = "Dob")
    public String Dob;

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
    @SerializedName("imageData")
    @ColumnInfo(name = "ImageData", typeAffinity = ColumnInfo.BLOB)
    public byte[] ImageData;

    @Expose
    @SerializedName("donorId_ServerSite")
    @ColumnInfo(name = "DonorId_ServerSite")
    public String DonorId_ServerSite ;


}
