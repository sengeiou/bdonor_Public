package com.sq.sohel.blooddonor.data.model.others;

import com.sq.sohel.blooddonor.data.model.db.Donor;
import com.sq.sohel.blooddonor.data.model.db.RequestBlood;
import com.sq.sohel.blooddonor.utils.AgeCalculator.AgeCalculator;
import com.sq.sohel.blooddonor.utils.AppConstants;
import com.sq.sohel.blooddonor.utils.DateTimeFormatUtils;
import com.sq.sohel.blooddonor.utils.StringUtils.StringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.sq.sohel.blooddonor.utils.AppConstants.DATE_FORMAT;
import static com.sq.sohel.blooddonor.utils.AppConstants.DISPLAY_DATE_FORMAT;

public class RequestBloodCardData {

    public RequestBlood blood;

    public RequestBloodCardData(RequestBlood blood) {
        this.blood = blood;
    }

    public String getDescription() {
        String str;
        if (blood.NoOfBags > 1) {
            str = blood.NoOfBags + " bags of " + blood.BloodType + " on " + DateTimeFormatUtils.DateFormatChange(blood.BloodNeedDate, DATE_FORMAT, DISPLAY_DATE_FORMAT) + " at " + blood.Address;
        } else {
            str = blood.NoOfBags + " bag of " + blood.BloodType + " on " + DateTimeFormatUtils.DateFormatChange(blood.BloodNeedDate, DATE_FORMAT, DISPLAY_DATE_FORMAT) + " at " + blood.Address;
        }

        return str;
    }

    public String getId() {
        return blood.Id;
    }

    public String getContactPerson1() {
        return blood.ContactPerson1;
    }

    public String getContactNumber1() {
        return blood.ContactNumber1;
    }

    public String getContactPerson2() {
        return blood.ContactPerson2;
    }

    public String getContactNumber2() {
        return blood.ContactNumber2;
    }

    public String getContactPerson3() {
        return blood.ContactPerson3;
    }

    public String getContactNumber3() {
        return blood.ContactNumber3;
    }

    public Double getLongitude() {
        return blood.Longitude;
    }

    public Double getLatitude() {
        return blood.Latitude;
    }

    public String getBloodType() {
        return blood.BloodType;
    }

    public String getComments() {
        return blood.Comments;
    }

    public String getFullAddress() {
        String returnAddress = "";
        String c = blood.City == null ? "" : blood.City;
        String country = blood.Country == null ? "" : blood.Country;
        returnAddress = blood.Address == null ? "" : blood.Address;
        if (!StringExtension.isNullOrWhiteSpace(c)) {
            returnAddress = returnAddress + ", " + c;
        }
        if (!StringExtension.isNullOrWhiteSpace(country)) {
            returnAddress = returnAddress + ", " + country;
        }
        return returnAddress;
    }

}
