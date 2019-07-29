
package com.sq.sohel.blooddonor.ui.becomeDonor.becomeHero;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class HeroFragmentProvider {

    @ContributesAndroidInjector(modules = HeroFragmentModule.class)
    abstract HeroFragment provideHeroFragmentFactory();
}
