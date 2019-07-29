package com.sq.sohel.blooddonor.ui.forgotPassword;

import android.databinding.ObservableField;

import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.data.model.api.SignUp.SignUpRequest;
import com.sq.sohel.blooddonor.data.model.others.ValidationModel;
import com.sq.sohel.blooddonor.ui.base.BaseViewModel;
import com.sq.sohel.blooddonor.ui.signUp.SignUpNavigator;
import com.sq.sohel.blooddonor.utils.StringUtils.StringExtension;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.sq.sohel.blooddonor.utils.ObservableFieldUtils.ObservableFieldExtension.getStringFromObservableField;

public class ForgotPasswordViewModel extends BaseViewModel<ForgotPasswordNavigator> {

    public ForgotPasswordViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }


    private ObservableField<String> email = new ObservableField<>();

    public ObservableField<String> getEmail() {
        return email;
    }

    public void setEmail(ObservableField<String> email) {
        this.email = email;
    }

    public void sendRecoveryEmail() {
        String email = getStringFromObservableField(this.email);
        if (StringExtension.isNullOrWhiteSpace(email)) {
            getNavigator().handleError(new Throwable("Email is required."));
            return;
        }
        if (getNavigator().isNetworkConnected()) {
            getDataManager().forgotPassword(email);
            getNavigator().recoveryEmailSend();

        } else {
            getNavigator().handleNoNetworkConnection();
        }


    }

    public void closeMe() {
        getNavigator().closeMe();

    }


}
