
package com.sq.sohel.blooddonor.ui.main.newRequestForBlood;


import android.databinding.ObservableField;
import android.util.Log;

import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.data.model.api.RequestBlood.RequestBloodRequest;
import com.sq.sohel.blooddonor.data.model.db.RequestBlood;
import com.sq.sohel.blooddonor.data.model.others.ValidationModel;
import com.sq.sohel.blooddonor.ui.base.BaseViewModel;
import com.sq.sohel.blooddonor.utils.AppConstants;
import com.sq.sohel.blooddonor.utils.DateTimeFormatUtils;
import com.sq.sohel.blooddonor.utils.StringUtils.StringExtension;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.sq.sohel.blooddonor.utils.AppConstants.DATE_FORMAT;
import static com.sq.sohel.blooddonor.utils.AppConstants.DISPLAY_DATE_FORMAT;
import static com.sq.sohel.blooddonor.utils.ObservableFieldUtils.ObservableFieldExtension.getIntegerFromObservableField;
import static com.sq.sohel.blooddonor.utils.ObservableFieldUtils.ObservableFieldExtension.getStringFromObservableField;

public class NewRequestViewModel extends BaseViewModel<NewRequestNavigator> {

    public String[] getBloodGroupList() {
        return AppConstants.BLOOD_GROUP_LIST;
    }

    public String[] getRequestForList() {
        return AppConstants.REQUEST_FOR_LIST;
    }

    public String[] getNoOfBagList() {
        int length = 30;
        String[] arr = new String[length];
        for (int i = 0; i < length; i++) {
            arr[i] = String.valueOf(i + 1);
        }
        return arr;
    }

    private ObservableField<String> id = new ObservableField<>();

    private ObservableField<String> bloodType = new ObservableField<>();
    private ObservableField<String> requestFor = new ObservableField<>(); //Blood or Platelet
    private ObservableField<Integer> noOfBags = new ObservableField<>(2);
    private ObservableField<String> bloodNeedDate = new ObservableField<>();
    private ObservableField<String> comments = new ObservableField<>();
    private ObservableField<String> contactPerson1Name = new ObservableField<>();
    private ObservableField<String> contactPerson1No = new ObservableField<>();
    private ObservableField<String> contactPerson2Name = new ObservableField<>();
    private ObservableField<String> contactPerson2No = new ObservableField<>();
    private ObservableField<String> contactPerson3Name = new ObservableField<>();
    private ObservableField<String> contactPerson3No = new ObservableField<>();


    private ObservableField<Double> longitude = new ObservableField<>();
    private ObservableField<Double> latitude = new ObservableField<>();
    private ObservableField<String> address = new ObservableField<>();
    private ObservableField<String> city = new ObservableField<>();
    private ObservableField<String> country = new ObservableField<>();
    private ObservableField<Boolean> isLocallyAdd = new ObservableField<>();
    private ObservableField<Boolean> isSyncWithServer = new ObservableField<>();
    private ObservableField<String> addedBy = new ObservableField<>();

    public ObservableField<String> getId() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public ObservableField<String> getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType.set(bloodType);
    }

    public ObservableField<String> getRequestFor() {
        return requestFor;
    }

    public void setRequestFor(String requestFor) {
        this.requestFor.set(requestFor);
    }

    public ObservableField<Integer> getNoOfBags() {
        return noOfBags;
    }

    public void setNoOfBags(Integer noOfBags) {
        this.noOfBags.set(noOfBags);
    }

    public ObservableField<String> getBloodNeedDate() {
        return bloodNeedDate;
    }

    public void setBloodNeedDate(ObservableField<String> bloodNeedDate) {
        this.bloodNeedDate = bloodNeedDate;
    }

    public ObservableField<String> getComments() {
        return comments;
    }

    public void setComments(ObservableField<String> comments) {
        this.comments = comments;
    }

    public ObservableField<String> getContactPerson1Name() {
        return contactPerson1Name;
    }

    public void setContactPerson1Name(ObservableField<String> contactPerson1Name) {
        this.contactPerson1Name = contactPerson1Name;
    }

    public ObservableField<String> getContactPerson1No() {
        return contactPerson1No;
    }

    public void setContactPerson1No(ObservableField<String> contactPerson1No) {
        this.contactPerson1No = contactPerson1No;
    }

    public ObservableField<String> getContactPerson2Name() {
        return contactPerson2Name;
    }

    public void setContactPerson2Name(ObservableField<String> contactPerson2Name) {
        this.contactPerson2Name = contactPerson2Name;
    }

    public ObservableField<String> getContactPerson2No() {
        return contactPerson2No;
    }

    public void setContactPerson2No(ObservableField<String> contactPerson2No) {
        this.contactPerson2No = contactPerson2No;
    }

    public ObservableField<String> getContactPerson3Name() {
        return contactPerson3Name;
    }

    public void setContactPerson3Name(ObservableField<String> contactPerson3Name) {
        this.contactPerson3Name = contactPerson3Name;
    }

    public ObservableField<String> getContactPerson3No() {
        return contactPerson3No;
    }

    public void setContactPerson3No(ObservableField<String> contactPerson3No) {
        this.contactPerson3No = contactPerson3No;
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


    public NewRequestViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onCancelClick() {
        getNavigator().closeMe();
    }

    public void onSubmitClick() {
        RequestBlood requestBlood = new RequestBlood();

        requestBlood.Id = UUID.randomUUID().toString();
        requestBlood.BloodType = getStringFromObservableField(getBloodType());
        requestBlood.RequestFor = getStringFromObservableField(getRequestFor());
        requestBlood.NoOfBags = getIntegerFromObservableField(getNoOfBags());
        requestBlood.BloodNeedDate = DateTimeFormatUtils.DateFormatChange(getStringFromObservableField(getBloodNeedDate()), DISPLAY_DATE_FORMAT, DATE_FORMAT);
        requestBlood.ContactPerson1 = getStringFromObservableField(getContactPerson1Name());
        requestBlood.ContactNumber1 = getStringFromObservableField(getContactPerson1No());
        requestBlood.ContactPerson2 = getStringFromObservableField(getContactPerson2Name());
        requestBlood.ContactNumber2 = getStringFromObservableField(getContactPerson2No());
        requestBlood.ContactPerson3 = getStringFromObservableField(getContactPerson3Name());
        requestBlood.ContactNumber3 = getStringFromObservableField(getContactPerson3No());
        requestBlood.Address = getStringFromObservableField(getAddress());
        requestBlood.City = getStringFromObservableField(getCity());
        requestBlood.Country = getStringFromObservableField(getCountry());

        requestBlood.Comments = getStringFromObservableField(getComments());
        requestBlood.IsLocallyAdd = true;
        requestBlood.IsSyncWithServer = false;
        requestBlood.AddedBy = getDataManager().getCurrentUserEmail();
        String strLatLong[] = getDataManager().getCurrentUserLatLong();
        if (strLatLong != null && strLatLong.length == 2) {
            requestBlood.Latitude = Double.valueOf(strLatLong[0]);
            requestBlood.Longitude = Double.valueOf(strLatLong[1]);
        }
        if (validateViewModel(requestBlood)) {
            if (getNavigator().isNetworkConnected()) {
                saveDonor(requestBlood);
                getNavigator().closeMe();
            } else {
                getNavigator().handleNoNetworkConnection();
            }
        }

    }

    private boolean validateViewModel(RequestBlood requestBlood) {
        boolean returnStatus = true;
        List<ValidationModel> viewModelError = getViewModelError();
        if (viewModelError.size() > 0) {
            getNavigator().handleError(viewModelError.get(0));
            return false;
        }

        if (requestBlood != null) {
            if (requestBlood.NoOfBags <= 0) {
                getNavigator().handleError(new Throwable("Number of bags should at least 1"));
                return false;
            }
            if (StringExtension.isNullOrWhiteSpace(requestBlood.ContactPerson1) && StringExtension.isNullOrWhiteSpace(requestBlood.ContactNumber1)
                    && StringExtension.isNullOrWhiteSpace(requestBlood.ContactPerson2) && StringExtension.isNullOrWhiteSpace(requestBlood.ContactNumber2)
                    && StringExtension.isNullOrWhiteSpace(requestBlood.ContactPerson3) && StringExtension.isNullOrWhiteSpace(requestBlood.ContactNumber3)) {
                getNavigator().handleError(new Throwable("Please provide at least one contact name and number."));
                return false;
            }

            if ((!StringExtension.isNullOrWhiteSpace(requestBlood.ContactPerson1) && StringExtension.isNullOrWhiteSpace(requestBlood.ContactNumber1)) ||
                    (StringExtension.isNullOrWhiteSpace(requestBlood.ContactPerson1) && !StringExtension.isNullOrWhiteSpace(requestBlood.ContactNumber1))) {
                getNavigator().handleError(new Throwable("Please provide contact person name and number"));
                return false;
            }
            if ((!StringExtension.isNullOrWhiteSpace(requestBlood.ContactPerson2) && StringExtension.isNullOrWhiteSpace(requestBlood.ContactNumber2)) ||
                    (StringExtension.isNullOrWhiteSpace(requestBlood.ContactPerson2) && !StringExtension.isNullOrWhiteSpace(requestBlood.ContactNumber2))) {
                getNavigator().handleError(new Throwable("Please provide contact person name and number"));
                return false;
            }
            if ((!StringExtension.isNullOrWhiteSpace(requestBlood.ContactPerson3) && StringExtension.isNullOrWhiteSpace(requestBlood.ContactNumber3)) ||
                    (StringExtension.isNullOrWhiteSpace(requestBlood.ContactPerson3) && !StringExtension.isNullOrWhiteSpace(requestBlood.ContactNumber3))) {
                getNavigator().handleError(new Throwable("Please provide contact person name and number"));
                return false;
            }
        }

        return returnStatus;
    }

    private void saveDonor(final RequestBlood requestBlood) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .saveRequestBlood(requestBlood)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(b -> {
                    if (b) {
                        getCompositeDisposable().add(
                                getDataManager().saveRequestBloodInfo(RequestBloodRequest.getInstance(requestBlood)).doOnSuccess(response -> {
                                    if (!StringExtension.isNullOrWhiteSpace(response.Data.get("id").getAsString())) {
                                        requestBlood.IsSyncWithServer = true;
                                        requestBlood.Id_ServerSite = response.Data.get("id").getAsString();
                                        getDataManager().saveRequestBlood(requestBlood).subscribe();
                                    }
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
    }

}
