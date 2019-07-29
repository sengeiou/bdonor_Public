

package com.sq.sohel.blooddonor.ui.main.donor;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import com.sq.sohel.blooddonor.ViewModelProviderFactory;
import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.ui.becomeDonor.becomeHero.HeroViewModel;
import com.sq.sohel.blooddonor.ui.main.MainActivity;
import com.sq.sohel.blooddonor.ui.main.MainPagerAdapter;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;


@Module
public class DonorFragmentModule {

    @Provides
    DonorViewModel donorViewModel(DataManager dataManager,
                                  SchedulerProvider schedulerProvider) {
        return new DonorViewModel(dataManager, schedulerProvider);
    }

    @Provides
    DonorAdapter provideDonorAdapter() {
        return new DonorAdapter(new ArrayList<>());
    }

    @Provides
    @Named("DonorFragment")
    ViewModelProvider.Factory provideDonorViewModel(DonorViewModel donorViewModel) {
        return new ViewModelProviderFactory<>(donorViewModel);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(DonorFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

    @Provides
    @Named("MainPagerAdapterDonorFragment")
    MainPagerAdapter provideMainPagerAdapter(DonorFragment fragment) {
        return new MainPagerAdapter(fragment.getFragmentManager(), fragment);
    }
}
