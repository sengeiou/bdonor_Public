
package com.sq.sohel.blooddonor.ui.main;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.text.TextUtils;

import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.data.model.others.DonorCardData;
import com.sq.sohel.blooddonor.ui.base.BaseViewModel;
import com.sq.sohel.blooddonor.data.model.others.ValidationModel;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

import java.util.List;

public class MainViewModel extends BaseViewModel<MainNavigator> {

    public static final int NO_ACTION = -1, ACTION_ADD_ALL = 0, ACTION_DELETE_SINGLE = 1;

    private final ObservableField<String> appVersion = new ObservableField<>();

    private final MutableLiveData<List<DonorCardData>> donorCardData;

    private final ObservableList<DonorCardData> donorCardDataList = new ObservableArrayList<>();


    private final ObservableField<String> userEmail = new ObservableField<>();
    private final ObservableField<String> userName = new ObservableField<>();
    private final ObservableField<String> userProfilePicUrl = new ObservableField<>();
    private final ObservableField<byte[]> userProfilePicData = new ObservableField<>();


    private int action = NO_ACTION;

    public MainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        donorCardData = new MutableLiveData<>();
        loadDonorCards();
    }

    public int getAction() {
        return action;
    }

    public ObservableField<String> getAppVersion() {
        return appVersion;
    }

    public MutableLiveData<List<DonorCardData>> getDonorCardData() {
        return donorCardData;
    }

    public ObservableList<DonorCardData> getDonorCardDataList() {
        return donorCardDataList;
    }

    public void setQuestionDataList(List<DonorCardData> questionCardDatas) {
        action = ACTION_ADD_ALL;
        donorCardDataList.clear();
        donorCardDataList.addAll(questionCardDatas);
    }

    public ObservableField<String> getUserEmail() {
        return userEmail;
    }

    public ObservableField<String> getUserName() {
        return userName;
    }

    public ObservableField<String> getUserProfilePicUrl() {
        return userProfilePicUrl;
    }

    public ObservableField<byte[]> getUserProfilePicData() {
        return userProfilePicData;
    }

    public void loadDonorCards() {
        getCompositeDisposable().add(getDataManager()
                .getDonorCardData()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(questionList -> {
                    if (questionList != null) {
                        action = ACTION_ADD_ALL;
                        donorCardData.setValue(questionList);
                    }
                }, throwable -> {

                }));
    }

    public void logout() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().doLogoutApiCall()
                .doOnSuccess(response -> getDataManager().setUserAsLoggedOut())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().openLoginActivity();
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void onNavMenuCreated() {

        final String currentUserEmail = getDataManager().getCurrentUserEmail();
        if (!TextUtils.isEmpty(currentUserEmail)) {
            userEmail.set(currentUserEmail);
        }

        getCompositeDisposable().add(getDataManager().getUserFromDBByEmail(currentUserEmail).subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(resp -> {
                    if (resp != null) {
                        userName.set(resp.userName);
                        userProfilePicUrl.set(resp.profilePicUrl);
                        userProfilePicData.set(resp.profilePicImageData);
                        getDataManager().updateUserProfilePicture(resp.profilePicImageData);
                    }
                }, throwable -> {

                }));

//        final String currentUserName = getDataManager().getCurrentUserName();
//        if (!TextUtils.isEmpty(currentUserName)) {
//            userName.set(currentUserName);
//        }
//
//
//
//        final String profilePicUrl = getDataManager().getCurrentUserProfilePicUrl();
//        if (!TextUtils.isEmpty(profilePicUrl)) {
//            userProfilePicUrl.set(profilePicUrl);
//        }
    }

//    public void removeQuestionCard() {
//        action = ACTION_DELETE_SINGLE;
//        donorCardDataList.remove(0);
//        donorCardData.getValue().remove(0);
//    }

    public void updateAppVersion(String version) {
        appVersion.set(version);
    }
}
