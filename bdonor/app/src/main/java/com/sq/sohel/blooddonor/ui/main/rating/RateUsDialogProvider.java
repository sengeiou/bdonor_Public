

package com.sq.sohel.blooddonor.ui.main.rating;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RateUsDialogProvider {

    @ContributesAndroidInjector(modules = RateUsDialogModule.class)
    abstract RateUsDialog provideRateUsDialogFactory();
}
