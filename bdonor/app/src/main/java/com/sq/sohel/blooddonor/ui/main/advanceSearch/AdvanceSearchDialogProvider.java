

package com.sq.sohel.blooddonor.ui.main.advanceSearch;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AdvanceSearchDialogProvider {

    @ContributesAndroidInjector(modules = AdvanceSearchDialogModule.class)
    abstract AdvanceSearchDialog provideAdvanceSearchDialogFactory();
}
