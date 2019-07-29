

package com.sq.sohel.blooddonor.ui.becomeDonor;

import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.ui.becomeDonor.BecomeDonorActivity;
import com.sq.sohel.blooddonor.ui.becomeDonor.BecomeDonorPagerAdapter;
import com.sq.sohel.blooddonor.ui.becomeDonor.BecomeDonorViewModel;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class BecomeDonorActivityModule {

    @Provides
    BecomeDonorPagerAdapter provideBecomeDonorPagerAdapter(BecomeDonorActivity activity) {
        return new BecomeDonorPagerAdapter(activity.getSupportFragmentManager());
    }

    @Provides
    BecomeDonorViewModel provideBecomeDonorViewModel(DataManager dataManager,
                                                     SchedulerProvider schedulerProvider) {
        return new BecomeDonorViewModel(dataManager, schedulerProvider);
    }
}
