package com.sq.sohel.blooddonor.data.model.api.Donor;

import android.util.Base64;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sq.sohel.blooddonor.data.model.db.Donor;

public class DonorRequest {


    public static DonorRequest getInstance(Donor donor) {
        DonorRequest donorRequest = new DonorRequest();
        donorRequest.Email = donor.Email;
        donorRequest.Address = donor.Address;
        donorRequest.City = donor.City;
        donorRequest.Country = donor.Country;
        donorRequest.Dob = donor.Dob;
        donorRequest.LastDonationDate = donor.LastDonationDate;
        //donor.Age = getIntegerFromObservableField(getAge());
        donorRequest.BloodType = donor.BloodType;
        donorRequest.ContactNumber = donor.ContactNumber;
        donorRequest.Gender = donor.Gender;
        donorRequest.HeightIN = donor.HeightIN;
        donorRequest.Id = donor.DonorId_ServerSite; // This is request model for server; SO it's should as it is server expect
        //donorRequest.ImageData = donor.ImageData;
        donorRequest.IsLocallyAdd = donor.IsLocallyAdd;
        donorRequest.IsSyncWithServer = donor.IsSyncWithServer;
        donorRequest.AddedBy = donor.AddedBy;
        donorRequest.Latitude = donor.Latitude;
        donorRequest.Longitude = donor.Longitude;

        donorRequest.Name = donor.Name;
        donorRequest.WeightLBS = donor.WeightLBS;
        donorRequest.DonorId_ClientSite = donor.Id; // This is request model for server; SO it's should as it is server expect
        if (donor.ImageData != null && donor.ImageData.length > 0) {
            //donorRequest.ImageDataInString = Base64.encodeToString(donor.ImageData, 0);
        }
        return donorRequest;
    }

    @Expose
    @SerializedName("id")
    public String Id;

    @Expose
    @SerializedName("name")
    public String Name;

    @Expose
    @SerializedName("email")
    public String Email;

    @Expose
    @SerializedName("gender")
    public String Gender;

    @Expose
    @SerializedName("contactNumber")
    public String ContactNumber;

    @Expose
    @SerializedName("longitude")
    public Double Longitude;

    @Expose
    @SerializedName("latitude")
    public Double Latitude;

    @Expose
    @SerializedName("bloodType")
    public String BloodType;

    @Expose
    @SerializedName("weightLBS")
    public Integer WeightLBS;

    @Expose
    @SerializedName("heightIN")
    public Integer HeightIN;

    @Expose
    @SerializedName("lastDonationDate")
    public String LastDonationDate;

    @Expose
    @SerializedName("address")
    public String Address;

    @Expose
    @SerializedName("city")
    public String City;

    @Expose
    @SerializedName("country")
    public String Country;

    @Expose
    @SerializedName("dob")
    public String Dob;

    @Expose
    @SerializedName("isLocallyAdd")
    public boolean IsLocallyAdd;

    @Expose
    @SerializedName("isSyncWithServer")
    public boolean IsSyncWithServer;

    @Expose
    @SerializedName("addedBy")
    public String AddedBy;

    @Expose
    @SerializedName("donorId_ClientSite")
    public String DonorId_ClientSite;

    //@Expose
    //@SerializedName("imageData")
    public byte[] ImageData;

    @Expose
    @SerializedName("imageDataInString")
    public String ImageDataInString;

}
