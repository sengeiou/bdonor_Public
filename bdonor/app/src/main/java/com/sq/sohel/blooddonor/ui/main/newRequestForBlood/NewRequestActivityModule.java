package com.sq.sohel.blooddonor.ui.main.newRequestForBlood;



import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class NewRequestActivityModule {

    @Provides
    NewRequestViewModel provideNewRequestViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new NewRequestViewModel(dataManager, schedulerProvider);
    }
}
