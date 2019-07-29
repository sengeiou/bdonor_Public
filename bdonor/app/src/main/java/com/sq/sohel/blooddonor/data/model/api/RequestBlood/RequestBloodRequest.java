package com.sq.sohel.blooddonor.data.model.api.RequestBlood;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sq.sohel.blooddonor.data.model.api.Donor.DonorRequest;
import com.sq.sohel.blooddonor.data.model.db.Donor;
import com.sq.sohel.blooddonor.data.model.db.RequestBlood;

public class RequestBloodRequest {

    public static RequestBloodRequest getInstance(RequestBlood requestBlood) {
        RequestBloodRequest requestBloodRequest = new RequestBloodRequest();


        requestBloodRequest.id = requestBlood.Id_ServerSite; // This is request model for server; SO it's should as it is server expect
        requestBloodRequest.id_ClientSite = requestBlood.Id; // This is request model for server; SO it's should as it is server expect

        requestBloodRequest.bloodType = requestBlood.BloodType;
        requestBloodRequest.requestFor = requestBlood.RequestFor;
        requestBloodRequest.noOfBags = requestBlood.NoOfBags;
        requestBloodRequest.bloodNeedDate = requestBlood.BloodNeedDate;
        requestBloodRequest.contactPerson1 = requestBlood.ContactPerson1;
        requestBloodRequest.contactNumber1 = requestBlood.ContactNumber1;
        requestBloodRequest.contactPerson2 = requestBlood.ContactPerson2;
        requestBloodRequest.contactNumber2 = requestBlood.ContactNumber2;
        requestBloodRequest.contactPerson3 = requestBlood.ContactPerson3;
        requestBloodRequest.contactNumber3 = requestBlood.ContactNumber3;
        requestBloodRequest.address = requestBlood.Address;
        requestBloodRequest.city = requestBlood.City;
        requestBloodRequest.country = requestBlood.Country;
        requestBloodRequest.comments = requestBlood.Comments;
        requestBloodRequest.isLocallyAdd = requestBlood.IsLocallyAdd;
        requestBloodRequest.isSyncWithServer = requestBlood.IsSyncWithServer;
        requestBloodRequest.addedBy = requestBlood.AddedBy;
        requestBloodRequest.latitude = requestBlood.Latitude;
        requestBloodRequest.longitude = requestBlood.Longitude;



        return  requestBloodRequest;
    }

    @Expose
    @SerializedName("id")
    public String id;

    @Expose
    @SerializedName("bloodType")
    public String bloodType;

    @Expose
    @SerializedName("requestFor")
    public String requestFor; //Blood or Platelet

    @Expose
    @SerializedName("noOfBags")
    public int noOfBags;

    @Expose
    @SerializedName("bloodNeedDate")
    public String bloodNeedDate;

    @Expose
    @SerializedName("comments")
    public String comments;

    @Expose
    @SerializedName("contactPerson1")
    public String contactPerson1;

    @Expose
    @SerializedName("contactNumber1")
    public String contactNumber1;

    @Expose
    @SerializedName("contactPerson2")
    public String contactPerson2;

    @Expose
    @SerializedName("contactNumber2")
    public String contactNumber2;

    @Expose
    @SerializedName("contactPerson3")
    public String contactPerson3;

    @Expose
    @SerializedName("contactNumber3")
    public String contactNumber3;

    @Expose
    @SerializedName("longitude")
    public Double longitude;

    @Expose
    @SerializedName("latitude")
    public Double latitude;

    @Expose
    @SerializedName("address")
    public String address;

    @Expose
    @SerializedName("city")
    public String city;

    @Expose
    @SerializedName("country")
    public String country;


    @Expose
    @SerializedName("isLocallyAdd")
    public boolean isLocallyAdd;

    @Expose
    @SerializedName("isSyncWithServer")
    public boolean isSyncWithServer;

    @Expose
    @SerializedName("addedBy")
    public String addedBy;
    @Expose
    @SerializedName("id_ClientSite")
    public String id_ClientSite;

}
