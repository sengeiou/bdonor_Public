

package com.sq.sohel.blooddonor.data.model.api;

import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.$Gson$Types;
import com.sq.sohel.blooddonor.data.model.db.Donor;

import java.lang.reflect.Type;
import java.util.List;

public class DonorListResponse extends BaseResponse {

    Gson mGson;
    @Expose
    @SerializedName("bloodDonorData")
    private List<Donor> bloodDonorData;

    @Expose
    @SerializedName("requestBloodData")
    private List<RequestBlood> requestBloodData;

    public List<Donor> getBloodDonorData() {
        if (super.Data != null) {
            mGson = new Gson();
            Type type = $Gson$Types.newParameterizedTypeWithOwner(null, List.class, Donor.class);
            try {
                bloodDonorData = mGson.fromJson(super.Data.getAsJsonArray("bloodDonorData"), type);
                for (int i = 0; i < bloodDonorData.size(); i++) {
                    JsonElement jsonElementImage = super.Data.getAsJsonArray("bloodDonorData").get(i).getAsJsonObject().get("image");
                    if (!(jsonElementImage instanceof JsonNull)) {
                        if (!(jsonElementImage.getAsJsonObject().get("imageData") instanceof JsonNull)) {
                            bloodDonorData.get(i).ImageData = Base64.decode(jsonElementImage.getAsJsonObject().get("imageData").toString(), Base64.DEFAULT);
                            ;
                        }
                    }
                    bloodDonorData.get(i).Id = super.Data.getAsJsonArray("bloodDonorData").get(i).getAsJsonObject().get("donorId_ClientSite").getAsString();
                    bloodDonorData.get(i).DonorId_ServerSite = super.Data.getAsJsonArray("bloodDonorData").get(i).getAsJsonObject().get("id").getAsString();

                }
            } catch (Exception ex) {

            }
        }
        return bloodDonorData;
    }

    public List<RequestBlood> getRequestBloodData() {
        if (super.Data != null) {
            mGson = new Gson();
            Type type = $Gson$Types.newParameterizedTypeWithOwner(null, List.class, RequestBlood.class);
            try {
                requestBloodData = mGson.fromJson(super.Data.getAsJsonArray("requestBloodData"), type);
                for (int i = 0; i < requestBloodData.size(); i++) {
                    requestBloodData.get(i).Id = super.Data.getAsJsonArray("requestBloodData").get(i).getAsJsonObject().get("id_ClientSite").getAsString();
                    requestBloodData.get(i).Id_ServerSite = super.Data.getAsJsonArray("requestBloodData").get(i).getAsJsonObject().get("id").getAsString();

                }
            } catch (Exception ex) {

            }
        }
        return requestBloodData;
    }


    public static class Donor {

        @Expose
        @SerializedName("id")
        private String Id;

        @Expose
        @SerializedName("donorId_ServerSite")
        private String DonorId_ServerSite;

        @Expose
        @SerializedName("name")
        private String Name;

        @Expose
        @SerializedName("email")
        private String Email;

        @Expose
        @SerializedName("gender")
        private String Gender;

        @Expose
        @SerializedName("contactNumber")
        private String ContactNumber;

        @Expose
        @SerializedName("longitude")
        private Double Longitude;

        @Expose
        @SerializedName("latitude")
        private Double Latitude;

        @Expose
        @SerializedName("bloodType")
        private String BloodType;

        @Expose
        @SerializedName("weightLBS")
        private Integer WeightLBS;

        @Expose
        @SerializedName("heightIN")
        private Integer HeightIN;

        @Expose
        @SerializedName("lastDonationDate")
        private String LastDonationDate;

        @Expose
        @SerializedName("address")
        private String Address;

        @Expose
        @SerializedName("city")
        private String City;

        @Expose
        @SerializedName("country")
        private String Country;

        @Expose
        @SerializedName("dob")
        private String Dob;

        @Expose
        @SerializedName("isLocallyAdd")
        private boolean IsLocallyAdd;

        @Expose
        @SerializedName("isSyncWithServer")
        private boolean IsSyncWithServer;

        @Expose
        @SerializedName("addedBy")
        private String AddedBy;

        @Expose
        @SerializedName("ImageData")
        private byte[] ImageData;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Donor)) {
                return false;
            }

            Donor blog = (Donor) o;
            return Name.equals(blog.Name);

        }

        @Override
        public int hashCode() {
            int result = Name.hashCode();
            return result;
        }


        public String getId() {
            return Id;
        }

        public String getName() {
            return Name;
        }

        public String getEmail() {
            return Email;
        }

        public String getGender() {
            return Gender;
        }

        public String getContactNumber() {
            return ContactNumber;
        }

        public Double getLongitude() {
            return Longitude;
        }

        public Double getLatitude() {
            return Latitude;
        }

        public String getBloodType() {
            return BloodType;
        }

        public Integer getWeightLBS() {
            return WeightLBS;
        }

        public Integer getHeightIN() {
            return HeightIN;
        }

        public String getLastDonationDate() {
            return LastDonationDate;
        }

        public String getAddress() {
            return Address;
        }

        public String getCity() {
            return City;
        }

        public String getCountry() {
            return Country;
        }

        public String getDob() {
            return Dob;
        }

        public boolean isLocallyAdd() {
            return IsLocallyAdd;
        }

        public boolean isSyncWithServer() {
            return IsSyncWithServer;
        }

        public String getAddedBy() {
            return AddedBy;
        }
        public String getDonorId_ServerSite() {
            return DonorId_ServerSite;
        }

        public byte[] getImageData() {
            return ImageData;
        }
    }

    public static class RequestBlood {

        @Expose
        @SerializedName("id")
        public String Id;

        @Expose
        @SerializedName("id_ServerSite")
        public String Id_ServerSite;


        @Expose
        @SerializedName("bloodType")
        public String BloodType;

        @Expose
        @SerializedName("requestFor")
        public String RequestFor; //Blood or Platelet

        @Expose
        @SerializedName("noOfBags")
        public int NoOfBags;

        @Expose
        @SerializedName("bloodNeedDate")
        public String BloodNeedDate;

        @Expose
        @SerializedName("comments")
        public String Comments;

        @Expose
        @SerializedName("contactPerson1")
        public String ContactPerson1;

        @Expose
        @SerializedName("contactNumber1")
        public String ContactNumber1;

        @Expose
        @SerializedName("contactPerson2")
        public String ContactPerson2;

        @Expose
        @SerializedName("contactNumber2")
        public String ContactNumber2;

        @Expose
        @SerializedName("contactPerson3")
        public String ContactPerson3;

        @Expose
        @SerializedName("contactNumber3")
        public String ContactNumber3;

        @Expose
        @SerializedName("longitude")
        public Double Longitude;

        @Expose
        @SerializedName("latitude")
        public Double Latitude;

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
        @SerializedName("isLocallyAdd")
        public boolean IsLocallyAdd;

        @Expose
        @SerializedName("isSyncWithServer")
        public boolean IsSyncWithServer;

        @Expose
        @SerializedName("addedBy")
        public String AddedBy;
    }
}