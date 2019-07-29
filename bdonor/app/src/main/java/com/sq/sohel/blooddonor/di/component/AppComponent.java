

package com.sq.sohel.blooddonor.di.component;


import android.app.Application;

import com.sq.sohel.blooddonor.BloodDonorApp;
import com.sq.sohel.blooddonor.di.builder.ActivityBuilder;
import com.sq.sohel.blooddonor.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent {

    void inject(BloodDonorApp app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
