package com.sq.sohel.blooddonor.ui.signUp;

import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.ui.login.LoginViewModel;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;


@Module
public class SignUpActivityModule {

    @Provides
    SignUpViewModel provideSignUpViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new SignUpViewModel(dataManager, schedulerProvider);
    }
}