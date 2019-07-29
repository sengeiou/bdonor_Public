package com.sq.sohel.blooddonor.ui.base;

import com.sq.sohel.blooddonor.data.model.others.ValidationModel;
import com.sq.sohel.blooddonor.utils.AppUtils;

public interface IBaseNavigator {
    void handleError(Throwable throwable);
    void handleError(ValidationModel validationModel);
    boolean isNetworkConnected();
    void handleNoNetworkConnection();
    void ShowMessage(String message, AppUtils.MessageType messageType);
    void openLoginActivity();
}
