package com.sq.sohel.blooddonor.ui.main.rating;



import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class RateUsDialogModule {

    @Provides
    RateUsViewModel provideRateUsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new RateUsViewModel(dataManager, schedulerProvider);
    }
}
