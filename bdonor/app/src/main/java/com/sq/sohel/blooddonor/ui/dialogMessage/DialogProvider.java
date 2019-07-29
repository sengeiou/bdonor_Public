

package com.sq.sohel.blooddonor.ui.dialogMessage;

import com.sq.sohel.blooddonor.ui.main.rating.RateUsDialog;
import com.sq.sohel.blooddonor.ui.main.rating.RateUsDialogModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DialogProvider {

    @ContributesAndroidInjector(modules = DialogModule.class)
    abstract DialogView provideDialogViewFactory();
}


