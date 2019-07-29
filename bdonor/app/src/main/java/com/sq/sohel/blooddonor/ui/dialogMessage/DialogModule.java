package com.sq.sohel.blooddonor.ui.dialogMessage;



import android.app.Dialog;

import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.ui.main.rating.RateUsViewModel;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class DialogModule {

    @Provides
    DialogViewModel provideDialogViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new DialogViewModel(dataManager, schedulerProvider);
    }
}

