package com.sq.sohel.blooddonor.data.model.others;

import android.arch.persistence.room.util.StringUtil;

import com.sq.sohel.blooddonor.data.model.db.Donor;
import com.sq.sohel.blooddonor.utils.AgeCalculator.AgeCalculator;
import com.sq.sohel.blooddonor.utils.AppConstants;
import com.sq.sohel.blooddonor.utils.StringUtils.StringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DonorCardData {

    public Donor donor;

    public DonorCardData(Donor donor) {
        this.donor = donor;
    }

    public String getId() {
        return donor.Id;
    }

    public String getName() {
        return donor.Name == null ? "" : donor.Name;
    }

    public String getEmail() {
        return donor.Email == null ? "" : donor.Email;
    }

    public String getGender() {
        if (!StringExtension.isNullOrWhiteSpace(donor.Gender)) {
            if (donor.Gender.length() == 1) {
                return donor.Gender.toUpperCase().equals("M") ? "Male" : "Female";
            }
            return donor.Gender;
        }
        return "";
    }

    public String getContactNumber() {
        return donor.ContactNumber == null ? "" : donor.ContactNumber;
    }

    public Double getLongitude() {
        return donor.Longitude;
    }

    public Double getLatitude() {
        return donor.Latitude;
    }

    public String getBloodType() {
        return donor.BloodType;
    }

    public Integer getWeightLBS() {
        return donor.WeightLBS;
    }

    public Integer getHeightIN() {
        return donor.HeightIN;
    }

    public String getLastDonationDate() {
        return donor.LastDonationDate;
    }

    public String getCity() {
        return donor.City;
    }

    public String getCountry() {
        return donor.Country;
    }

    public String getFullAddress() {
        String returnAddress = "";
        String c = donor.City == null ? "" : donor.City;
        String country = donor.Country == null ? "" : donor.Country;
        returnAddress = donor.Address == null ? "" : donor.Address;
        if (!StringExtension.isNullOrWhiteSpace(c)) {
            returnAddress = returnAddress + ", " + c;
        }
        if (!StringExtension.isNullOrWhiteSpace(country)) {
            returnAddress = returnAddress + ", " + country;
        }
        return returnAddress;
    }

    public String getFullAge() {
        String dobString = "";
        SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.DATE_FORMAT);
        try {
            if (donor.Dob != null) {
                Date birthDate = sdf.parse(donor.Dob);
                dobString = AgeCalculator.calculateFullAge(birthDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return dobString;
    }

    public Integer getAge() {
        Integer dob = null;
        SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.DATE_FORMAT);
        try {
            if (donor.Dob != null) {
                Date birthDate = sdf.parse(donor.Dob);
                dob = Integer.valueOf(AgeCalculator.calculateOnlyAge(birthDate));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return dob;
    }
}
