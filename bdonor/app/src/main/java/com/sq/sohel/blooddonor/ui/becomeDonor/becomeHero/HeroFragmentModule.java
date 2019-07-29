

package com.sq.sohel.blooddonor.ui.becomeDonor.becomeHero;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import com.sq.sohel.blooddonor.ViewModelProviderFactory;
import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;


@Module
public class HeroFragmentModule {

    @Provides
    HeroViewModel heroViewModel(DataManager dataManager,
                                SchedulerProvider schedulerProvider) {
        return new HeroViewModel(dataManager, schedulerProvider);
    }

//    @Provides
//    HeroAdapter provideHeroAdapter() {
//        return new HeroAdapter(new ArrayList<>());
//    }

    @Provides
    @Named("HeroFragment")
    ViewModelProvider.Factory provideHeroViewModel(HeroViewModel heroViewModel) {
        return new ViewModelProviderFactory<>(heroViewModel);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(HeroFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
