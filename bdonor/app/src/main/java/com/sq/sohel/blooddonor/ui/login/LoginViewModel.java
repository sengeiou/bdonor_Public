package com.sq.sohel.blooddonor.ui.login;

import android.text.TextUtils;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.data.model.api.Login.LoginRequest;
import com.sq.sohel.blooddonor.ui.base.BaseViewModel;
import com.sq.sohel.blooddonor.utils.CommonUtils;
import com.sq.sohel.blooddonor.data.model.others.ValidationModel;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

import org.json.JSONObject;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    public LoginViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }


    public boolean isEmailAndPasswordValid(String email, String password) {
        // validate email and password
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        if (!CommonUtils.isEmailValid(email)) {
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        return true;
    }



    private void getCompanyContactInfo(String contactId, String email, String accessToken) {
        if (getNavigator().isNetworkConnected()) {
            setIsLoading(true);
            getCompositeDisposable().add(getDataManager().getCompanyContactInfo(contactId, accessToken).doOnSuccess(response ->
                    getDataManager()
                            .updateUserInfo(
                                    accessToken,
                                    email,
                                    DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER,
                                    response.get("name").getAsString(),
                                    email,
                                    "", null)
            ).subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(response -> {
                        setIsLoading(false);
                        getNavigator().openSplashActivity();
                    }, throwable -> {
                        setIsLoading(false);
                        getNavigator().handleError(throwable);
                    }));
        } else {
            getNavigator().handleNoNetworkConnection();
        }
    }

    private void getUserInformation(String email, String accessToken) {
        //api/users/{email}
        if (getNavigator().isNetworkConnected()) {
            setIsLoading(true);
            getCompositeDisposable().add(getDataManager().getUserInfo(email, accessToken).doOnSuccess(response ->
                    getCompanyContactInfo(response.get("xptoUserId").getAsString(), email, accessToken)
            ).subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(response -> {
                        //setIsLoading(false);
                        //getNavigator().openSplashActivity();
                    }, throwable -> {
                        setIsLoading(false);
                        getNavigator().handleNoNetworkConnection();
                        getNavigator().handleError(throwable);
                    }));
        } else {
            getNavigator().handleNoNetworkConnection();
        }
    }

    public void login(String email, String password) {
        if (getNavigator().isNetworkConnected()) {
            setIsLoading(true);
            getCompositeDisposable().add(getDataManager()
                    .doServerLoginApiCall(new LoginRequest.ServerLoginRequest(email, password))
                    .doOnSuccess(response -> getUserInformation(email, response.getAccessToken())

                    )
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(response -> {
                        //setIsLoading(false);
                        //getNavigator().openSplashActivity();
                    }, throwable -> {
                        setIsLoading(false);
                        getNavigator().handleNoNetworkConnection();
                        getNavigator().handleError(throwable);
                    }));
        } else {
            getNavigator().handleNoNetworkConnection();
        }
    }

    public void onFbLoginClick(String accessToken, JSONObject fbAccount) {
        if (getNavigator().isNetworkConnected()) {
            setIsLoading(true);
            getCompositeDisposable().add(getDataManager()
                    .doFacebookLoginApiCall(new LoginRequest.FacebookLoginRequest(accessToken, fbAccount))
                    .doOnSuccess(response -> getDataManager()
                            .updateUserInfo(
                                    response.getAccessToken(),
                                    response.getUserId(),
                                    DataManager.LoggedInMode.LOGGED_IN_MODE_FB,
                                    response.getUserName(),
                                    response.getUserEmail(),
                                    response.getGoogleProfilePicUrl(), null))
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(response -> {
                        setIsLoading(false);
                        getNavigator().openSplashActivity();
                    }, throwable -> {
                        setIsLoading(false);
                        getNavigator().handleNoNetworkConnection();
                        getNavigator().handleError(throwable);
                    }));
        } else {
            getNavigator().handleNoNetworkConnection();

        }
    }

    public void onGoogleLoginClick(GoogleSignInAccount account) {
        if (getNavigator().isNetworkConnected()) {
            setIsLoading(true);
            getCompositeDisposable().add(getDataManager()
                    .doGoogleLoginApiCall(new LoginRequest.GoogleLoginRequest(account))
                    .doOnSuccess(response -> getDataManager()
                            .updateUserInfo(
                                    response.getAccessToken(),
                                    response.getUserId(),
                                    DataManager.LoggedInMode.LOGGED_IN_MODE_GOOGLE,
                                    response.getUserName(),
                                    response.getUserEmail(),
                                    response.getGoogleProfilePicUrl(), null))
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(response -> {
                        setIsLoading(false);
                        getNavigator().openSplashActivity();
                    }, throwable -> {
                        setIsLoading(false);
                        getNavigator().handleNoNetworkConnection();
                        getNavigator().handleError(throwable);
                    }));
        } else {
            getNavigator().handleNoNetworkConnection();
        }
    }

    public void onServerLoginClick() {
        getNavigator().login();
    }

    public void onSignUpClick() {
        getNavigator().signUp();
    }

    public void onForgotPasswordClick() {
        getNavigator().forgotPassword();
    }


}
