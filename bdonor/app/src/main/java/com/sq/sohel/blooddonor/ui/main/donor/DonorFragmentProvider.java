package com.sq.sohel.blooddonor.ui.main.donor;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DonorFragmentProvider {

    @ContributesAndroidInjector(modules = DonorFragmentModule.class)
    abstract DonorFragment provideDonorFragmentFactory();
}
