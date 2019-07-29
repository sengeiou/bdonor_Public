package com.sq.sohel.blooddonor.ui.requestAndResponseForBlood;

import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class RequestAndResponseForBloodActivityModule {
    @Provides
    RequestAndResponseForBloodPageAdapter provideRequestAndResponseForBloodPagerAdapter(RequestAndResponseForBloodActivity activity) {
        return new RequestAndResponseForBloodPageAdapter(activity.getSupportFragmentManager());
    }

    @Provides
    RequestAndResponseForBloodViewModel provideRequestAndResponseForBloodViewModel(DataManager dataManager,
                                                                                   SchedulerProvider schedulerProvider) {
        return new RequestAndResponseForBloodViewModel(dataManager, schedulerProvider);
    }
}
