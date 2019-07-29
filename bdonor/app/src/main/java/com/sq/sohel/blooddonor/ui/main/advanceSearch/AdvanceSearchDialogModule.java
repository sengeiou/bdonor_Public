package com.sq.sohel.blooddonor.ui.main.advanceSearch;



import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class AdvanceSearchDialogModule {

    @Provides
    AdvanceSearchViewModel provideAdvanceSearchViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new AdvanceSearchViewModel(dataManager, schedulerProvider);
    }
}
