

package com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import com.sq.sohel.blooddonor.ViewModelProviderFactory;
import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood.RequestBloodAdapter;
import com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood.RequestBloodFragment;
import com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood.RequestBloodViewModel;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;


@Module
public class RequestBloodFragmentModule {

    @Provides
    RequestBloodViewModel requestBloodViewModel(DataManager dataManager,
                                SchedulerProvider schedulerProvider) {
        return new RequestBloodViewModel(dataManager, schedulerProvider);
    }

    @Provides
    RequestBloodAdapter provideRequestBloodAdapter() {
        return new RequestBloodAdapter(new ArrayList<>());
    }

    @Provides
    @Named("RequestBloodFragment")
    ViewModelProvider.Factory provideRequestBloodViewModel(RequestBloodViewModel requestBloodViewModel) {
        return new ViewModelProviderFactory<>(requestBloodViewModel);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(RequestBloodFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
