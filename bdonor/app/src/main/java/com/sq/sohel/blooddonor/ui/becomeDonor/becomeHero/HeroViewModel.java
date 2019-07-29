package com.sq.sohel.blooddonor.ui.becomeDonor.becomeHero;

import android.annotation.SuppressLint;
import android.databinding.ObservableField;
import android.util.Log;

import com.google.gson.Gson;
import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.data.model.api.Donor.DonorImageRequest;
import com.sq.sohel.blooddonor.data.model.api.Donor.DonorRequest;
import com.sq.sohel.blooddonor.data.model.api.Donor.DonorResponse;
import com.sq.sohel.blooddonor.data.model.db.Donor;
import com.sq.sohel.blooddonor.ui.base.BaseViewModel;
import com.sq.sohel.blooddonor.ui.base.IBaseNavigator;
import com.sq.sohel.blooddonor.utils.AppConstants;
import com.sq.sohel.blooddonor.data.model.others.ValidationModel;
import com.sq.sohel.blooddonor.utils.DateTimeFormatUtils;
import com.sq.sohel.blooddonor.utils.NetworkUtils;
import com.sq.sohel.blooddonor.utils.StringUtils.StringExtension;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import io.reactivex.disposables.CompositeDisposable;

import static com.sq.sohel.blooddonor.utils.AppConstants.API_RESULT_SUCCESS;
import static com.sq.sohel.blooddonor.utils.AppConstants.DATE_FORMAT;
import static com.sq.sohel.blooddonor.utils.AppConstants.DISPLAY_DATE_FORMAT;
import static com.sq.sohel.blooddonor.utils.ObservableFieldUtils.ObservableFieldExtension.getBooleanFromObservableField;
import static com.sq.sohel.blooddonor.utils.ObservableFieldUtils.ObservableFieldExtension.getByteArrayFromObservableField;
import static com.sq.sohel.blooddonor.utils.ObservableFieldUtils.ObservableFieldExtension.getIntegerFromObservableField;
import static com.sq.sohel.blooddonor.utils.ObservableFieldUtils.ObservableFieldExtension.getStringFromObservableField;


public class HeroViewModel extends BaseViewModel<HeroNavigator> {

    public String[] getBloodGroupList() {
        return AppConstants.BLOOD_GROUP_LIST;
    }

    public String[] getGenderList() {
        return AppConstants.GENDER_LIST;
    }

    private ObservableField<String> id = new ObservableField<>();

    private ObservableField<String> imageUrl = new ObservableField<>();
    private ObservableField<String> name = new ObservableField<>();
    private ObservableField<String> email = new ObservableField<>();
    private ObservableField<String> gender = new ObservableField<>();
    private ObservableField<String> contactNumber = new ObservableField<>();
    private ObservableField<Double> longitude = new ObservableField<>();
    private ObservableField<Double> latitude = new ObservableField<>();

    private ObservableField<String> bloodType = new ObservableField<>();
    private ObservableField<Integer> selectedBloodTypeIndex = new ObservableField<>();

    private ObservableField<Integer> weightLBS = new ObservableField<>();
    private ObservableField<Integer> heightIN = new ObservableField<>();
    private ObservableField<String> lastDonationDate = new ObservableField<>();
    private ObservableField<String> address = new ObservableField<>();

    private ObservableField<String> city = new ObservableField<>();
    private ObservableField<String> country = new ObservableField<>();
    private ObservableField<String> dOB = new ObservableField<>();
    private ObservableField<Integer> age = new ObservableField<>();
    private ObservableField<Boolean> isLocallyAdd = new ObservableField<>();
    private ObservableField<Boolean> isSyncWithServer = new ObservableField<>();
    private ObservableField<String> addedBy = new ObservableField<>();
    private ObservableField<byte[]> imageData = new ObservableField<>();

    public ObservableField<String> getDonorId_serverSite() {
        return donorId_serverSite;
    }

    public void setDonorId_serverSite(String donorId_serverSite) {
        this.donorId_serverSite.set(donorId_serverSite);
    }

    private ObservableField<String> donorId_serverSite = new ObservableField<>();


    public ObservableField<String> getId() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public ObservableField<String> getImageUrl() {

        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl.set(imageUrl);
    }

    public ObservableField<String> getName() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObservableField<String> getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public ObservableField<String> getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public ObservableField<String> getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber.set(contactNumber);
    }

    public ObservableField<Double> getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude.set(longitude);
    }

    public ObservableField<Double> getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude.set(latitude);
    }

    public ObservableField<String> getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType.set(bloodType);
    }

    public ObservableField<Integer> getSelectedBloodTypeIndex() {
        return selectedBloodTypeIndex;
    }

    public void setSelectedBloodTypeIndex(Integer selectedBloodTypeIndex) {
        this.selectedBloodTypeIndex.set(selectedBloodTypeIndex);
    }

    public ObservableField<Integer> getWeightLBS() {
        return weightLBS;
    }

    public void setWeightLBS(Integer weightLBS) {
        this.weightLBS.set(weightLBS);
    }

    public ObservableField<Integer> getHeightIN() {
        return heightIN;
    }

    public void setHeightIN(Integer heightIN) {
        this.heightIN.set(heightIN);
    }

    public ObservableField<String> getLastDonationDate() {
        return lastDonationDate;
    }

    public void setLastDonationDate(String lastDonationDate) {
        this.lastDonationDate.set(lastDonationDate);
    }

    public ObservableField<String> getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public ObservableField<String> getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public ObservableField<String> getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public ObservableField<String> getDOB() {
        return dOB;
    }

    public void setDOB(String dOB) {
        this.dOB.set(dOB);
    }

    public ObservableField<Integer> getAge() {
        return age;
    }

    public void setAge(ObservableField<Integer> age) {
        this.age = age;
    }

    public ObservableField<Boolean> getIsLocallyAdd() {
        return isLocallyAdd;
    }

    public void setIsLocallyAdd(Boolean isLocallyAdd) {
        this.isLocallyAdd.set(isLocallyAdd);
    }

    public ObservableField<Boolean> getIsSyncWithServer() {
        return isSyncWithServer;
    }

    public void setIsSyncWithServer(Boolean isSyncWithServer) {
        this.isSyncWithServer.set(isSyncWithServer);
    }

    public ObservableField<String> getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy.set(addedBy);
    }

    public ObservableField<byte[]> getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData.set(imageData);
    }

    public HeroViewModel(DataManager dataManager,
                         SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void fetchHero(String emailAddress) {
        setIsLoading(true);
        if (emailAddress.equals(getDataManager().getCurrentUserEmail())) {
            setEmail(getDataManager().getCurrentUserEmail());
            setName(getDataManager().getCurrentUserName());
            setContactNumber(getDataManager().getCurrentUserContactNumber());
            setImageUrl(getDataManager().getCurrentUserProfilePicUrl());
        }
        getCompositeDisposable().add(getDataManager()
                .getDonorByEmail(emailAddress)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(d -> {
                    if (d != null) {
                        setId(d.Id);
                        setName(d.Name);
                        setEmail(d.Email);
                        setGender(d.Gender);
                        setContactNumber(d.ContactNumber);
                        setLongitude(d.Longitude);
                        setLatitude(d.Latitude);
                        setBloodType(d.BloodType);
                        setWeightLBS(d.WeightLBS);
                        setHeightIN(d.HeightIN);
                        setLastDonationDate(DateTimeFormatUtils.DateFormatChange(d.LastDonationDate, DATE_FORMAT, DISPLAY_DATE_FORMAT));
                        setAddress(d.Address);
                        setCity(d.City);
                        setCountry(d.Country);
                        setDOB(DateTimeFormatUtils.DateFormatChange(d.Dob, DATE_FORMAT, DISPLAY_DATE_FORMAT));
                        setIsLocallyAdd(d.IsLocallyAdd);
                        setIsSyncWithServer(d.IsSyncWithServer);
                        setAddedBy(d.AddedBy);
                        setImageData(d.ImageData);
                        setDonorId_serverSite(d.DonorId_ServerSite);
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    //getNavigator().handleError(throwable);
                }));
    }

    private void saveDonorImage(final Donor donor) {
        if (donor.ImageData != null) {
            getCompositeDisposable().add(getDataManager().saveDonorImageInfo(DonorImageRequest.getInstance(donor)).doOnSuccess(response -> {
                donor.DonorId_ServerSite = response.Data.get("id").getAsString();
                donor.IsSyncWithServer = true;
                getDataManager().saveDonor(donor).subscribe();
            }).doOnDispose(() -> {

            }).subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(response -> {
                                setIsLoading(false);
                            }
                            , throwable -> {
                                setIsLoading(false);
                                getNavigator().handleNoNetworkConnection();
                                getNavigator().handleError(throwable);
                            }));
        }
    }


    public void saveDonor(final Donor donor) {
        setIsLoading(true);
        if (getNavigator().isNetworkConnected()) {
            getCompositeDisposable().add(getDataManager()
                    .saveDonor(donor)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(b -> {
                        if (b) {
                            getCompositeDisposable().add(
                                    getDataManager().saveDonorInfo(DonorRequest.getInstance(donor)).doOnSuccess(donorResponse -> {
                                        donor.DonorId_ServerSite = donorResponse.Data.get("id").getAsString();
                                        getDataManager().saveDonor(donor).subscribe();
                                        if (donor.ImageData != null) {
                                            saveDonorImage(donor);
                                        } else {
                                            donor.IsSyncWithServer = true;
                                            getDataManager().saveDonor(donor).subscribe();
                                        }
                                    }).doOnDispose(() -> {

                                    }).subscribeOn(getSchedulerProvider().io())
                                            .observeOn(getSchedulerProvider().ui())
                                            .subscribe(response -> {
                                                        setIsLoading(false);
                                                    }
                                                    , throwable -> {
                                                        setIsLoading(false);
                                                        getNavigator().handleNoNetworkConnection();
                                                        getNavigator().handleError(throwable);
                                                    }));
                        }
                    }, throwable -> {
                        setIsLoading(false);
                        getNavigator().handleError(throwable);
                    }));
        } else {
            getNavigator().handleNoNetworkConnection();
        }
    }

    private boolean validateViewModel(Donor donor) {
        boolean returnStatus = true;
        List<ValidationModel> viewModelError = getViewModelError();
        if (viewModelError.size() > 0) {
            getNavigator().handleError(viewModelError.get(0));
            return false;
        }

        if (donor != null) {
            if (!StringExtension.isNullOrWhiteSpace(donor.Dob) && !StringExtension.isNullOrWhiteSpace(donor.LastDonationDate)) {
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
                try {
                    if (sdf.parse(donor.Dob).getTime() >= sdf.parse(donor.LastDonationDate).getTime()) {
                        getNavigator().handleError(new Throwable("Donation date can't be before date of birth."));
                        return false;
                    }
                } catch (ParseException e) {

                }
            }
        }

        return returnStatus;
    }

    public void onCancelClick() {
        getNavigator().closeHero();
    }

    public void onSubmitClick() {
        Donor donor = new Donor();
        if (StringExtension.isNullOrWhiteSpace(getStringFromObservableField(getId()))) {
            donor.Id = UUID.randomUUID().toString();
            donor.AddedBy = getDataManager().getCurrentUserEmail();
        } else {
            donor.Id = getStringFromObservableField(getId());
            donor.AddedBy = getStringFromObservableField(getAddedBy());
        }


        donor.Email = getStringFromObservableField(getEmail());
        donor.Address = getStringFromObservableField(getAddress());
        donor.City = getStringFromObservableField(getCity());
        donor.Country = getStringFromObservableField(getCountry());
        donor.Dob = DateTimeFormatUtils.DateFormatChange(getStringFromObservableField(getDOB()), DISPLAY_DATE_FORMAT, DATE_FORMAT);
        donor.LastDonationDate = DateTimeFormatUtils.DateFormatChange(getStringFromObservableField(getLastDonationDate()), DISPLAY_DATE_FORMAT, DATE_FORMAT);
        //donor.Age = getIntegerFromObservableField(getAge());
        donor.BloodType = getStringFromObservableField(getBloodType());
        donor.ContactNumber = getStringFromObservableField(getContactNumber());
        donor.Gender = getStringFromObservableField(getGender());
        donor.HeightIN = getIntegerFromObservableField(getHeightIN());
        donor.ImageData = getByteArrayFromObservableField(getImageData());
        donor.IsLocallyAdd = true;
        donor.IsSyncWithServer = getBooleanFromObservableField(getIsSyncWithServer());
        donor.DonorId_ServerSite = getStringFromObservableField(getDonorId_serverSite());

        String strLatLong[] = getDataManager().getCurrentUserLatLong();
        if (strLatLong != null && strLatLong.length == 2) {
            donor.Latitude = Double.valueOf(strLatLong[0]);
            donor.Longitude = Double.valueOf(strLatLong[1]);
        }
        donor.Name = getStringFromObservableField(getName());
        donor.WeightLBS = getIntegerFromObservableField(getWeightLBS());
        if (validateViewModel(donor)) {
            if (getNavigator().isNetworkConnected()) {
                saveDonor(donor);
                getNavigator().closeHero();
            } else {
                getNavigator().handleNoNetworkConnection();
            }
        }
    }
}
