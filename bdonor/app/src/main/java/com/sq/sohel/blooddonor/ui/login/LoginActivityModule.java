package com.sq.sohel.blooddonor.ui.login;



import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;


@Module
public class LoginActivityModule {

    @Provides
    LoginViewModel provideLoginViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new LoginViewModel(dataManager, schedulerProvider);
    }
}
