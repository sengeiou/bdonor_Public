package com.sq.sohel.blooddonor.ui.login;


import com.sq.sohel.blooddonor.data.model.others.ValidationModel;
import com.sq.sohel.blooddonor.ui.base.IBaseNavigator;

public interface LoginNavigator extends IBaseNavigator {

    void login();
    void openSplashActivity();
    void signUp();
    void forgotPassword();

}
