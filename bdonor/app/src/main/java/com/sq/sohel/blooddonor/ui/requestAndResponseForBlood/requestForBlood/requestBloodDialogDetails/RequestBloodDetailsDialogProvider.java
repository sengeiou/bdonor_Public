package com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood.requestBloodDialogDetails;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RequestBloodDetailsDialogProvider {

    @ContributesAndroidInjector(modules = RequestBloodDetailsDialogModule.class)
    abstract RequestBloodDetailsDialog provideRequestBloodDetailsDialogFactory();
}
