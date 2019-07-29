package com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RequestBloodFragmentProvider {

    @ContributesAndroidInjector(modules = RequestBloodFragmentModule.class)
    abstract RequestBloodFragment provideRequestBloodFragmentFactory();
}
