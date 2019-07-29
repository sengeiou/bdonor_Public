package com.sq.sohel.blooddonor.data.model.api.Donor;

import android.util.Base64;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sq.sohel.blooddonor.data.model.db.Donor;

public class DonorImageRequest {


    public static DonorImageRequest getInstance(Donor donor) {
        DonorImageRequest donorRequest = new DonorImageRequest();
        donorRequest.Id = donor.DonorId_ServerSite; // This is request model for server; SO it's should as it is server expect
        donorRequest.AddedBy = donor.AddedBy;
        donorRequest.Email = donor.Email;
        if (donor.ImageData != null && donor.ImageData.length > 0) {
            donorRequest.ImageDataInString = Base64.encodeToString(donor.ImageData, 0);
        }
        return donorRequest;
    }

    @Expose
    @SerializedName("id")
    public String Id;

    @Expose
    @SerializedName("addedBy")
    public String AddedBy;

    @Expose
    @SerializedName("email")
    public String Email;

    @Expose
    @SerializedName("imageDataInString")
    public String ImageDataInString;

}
