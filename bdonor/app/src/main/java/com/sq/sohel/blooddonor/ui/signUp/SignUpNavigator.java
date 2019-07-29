package com.sq.sohel.blooddonor.ui.signUp;

import com.sq.sohel.blooddonor.data.model.others.ValidationModel;
import com.sq.sohel.blooddonor.ui.base.IBaseNavigator;
import com.sq.sohel.blooddonor.ui.splash.SplashNavigator;

public interface SignUpNavigator extends IBaseNavigator {

    void closeMe();
    void confirmationEmailSend();
    void signUp();
}