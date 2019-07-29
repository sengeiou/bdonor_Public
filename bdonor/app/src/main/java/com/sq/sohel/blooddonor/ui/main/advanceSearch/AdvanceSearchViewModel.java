
package com.sq.sohel.blooddonor.ui.main.advanceSearch;


import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.ui.base.BaseViewModel;
import com.sq.sohel.blooddonor.data.model.others.ValidationModel;
import com.sq.sohel.blooddonor.utils.AppConstants;
import com.sq.sohel.blooddonor.utils.StringUtils.StringExtension;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

public class AdvanceSearchViewModel extends BaseViewModel<AdvanceSearchCallback> {

    public AdvanceSearchViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    @Expose
    @SerializedName("id")
    private String Id;

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
    @SerializedName("minAge")
    private String MinAge = "18";

    @Expose
    @SerializedName("maxAge")
    private String MaxAge = "50";

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

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getAddress() {
        return Address;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public void setBloodType(String bloodType) {
        BloodType = bloodType;
    }

    public void setWeightLBS(Integer weightLBS) {
        WeightLBS = weightLBS;
    }

    public void setHeightIN(Integer heightIN) {
        HeightIN = heightIN;
    }

    public String getMinAge() {
        return MinAge;
    }

    public void setMinAge(String minAge) {
        MinAge = minAge;
    }

    public String getMaxAge() {
        return MaxAge;
    }

    public void setMaxAge(String maxAge) {
        MaxAge = maxAge;
    }

    public void setLastDonationDate(String lastDonationDate) {
        LastDonationDate = lastDonationDate;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String[] getBloodGroupList() {
        return AppConstants.BLOOD_GROUP_LIST;
    }

    public String[] getGenderList() {
        return AppConstants.GENDER_LIST;
    }

    public void onCancelClick() {
        getNavigator().dismissDialog(true);
    }

    public void onSubmitClick() {
        getNavigator().dismissDialog(false);
    }

    public void onGenderSelect(String gender) {
        switch (gender) {
            case "A":
                setGender(null);
                break;
            case "M":
                setGender(AppConstants.GENDER_LIST[0]);
            case "F":
                setGender(AppConstants.GENDER_LIST[1]);
                break;
            default:
                setGender(null);
                break;
        }
    }

}
