package com.sq.sohel.blooddonor.ui.forgotPassword;

import com.sq.sohel.blooddonor.data.model.others.ValidationModel;
import com.sq.sohel.blooddonor.ui.base.IBaseNavigator;

public interface ForgotPasswordNavigator extends IBaseNavigator {
    void closeMe();
    void recoveryEmailSend();
}