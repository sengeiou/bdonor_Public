package com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood.requestBloodDialogDetails;

import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class RequestBloodDetailsDialogModule {

    @Provides
    RequestBloodDialogViewModel provideRequestBloodDialogViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new RequestBloodDialogViewModel(dataManager, schedulerProvider);
    }
}
