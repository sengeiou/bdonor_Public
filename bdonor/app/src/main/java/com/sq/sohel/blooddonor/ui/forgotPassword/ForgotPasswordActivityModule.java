package com.sq.sohel.blooddonor.ui.forgotPassword;

import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.ui.signUp.SignUpViewModel;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;


@Module
public class ForgotPasswordActivityModule {

    @Provides
    ForgotPasswordViewModel provideForgotPasswordViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new ForgotPasswordViewModel(dataManager, schedulerProvider);
    }
}