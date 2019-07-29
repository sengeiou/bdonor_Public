
package com.sq.sohel.blooddonor.di.module;


import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sq.sohel.blooddonor.BuildConfig;
import com.sq.sohel.blooddonor.R;
import com.sq.sohel.blooddonor.data.AppDataManager;
import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.data.local.db.AppDatabase;
import com.sq.sohel.blooddonor.data.local.db.AppDbHelper;
import com.sq.sohel.blooddonor.data.local.db.DbHelper;
import com.sq.sohel.blooddonor.data.local.prefs.AppPreferencesHelper;
import com.sq.sohel.blooddonor.data.local.prefs.PreferencesHelper;
import com.sq.sohel.blooddonor.data.remote.ApiHeader;
import com.sq.sohel.blooddonor.data.remote.ApiHelper;
import com.sq.sohel.blooddonor.data.remote.AppApiHelper;
import com.sq.sohel.blooddonor.di.ApiInfo;
import com.sq.sohel.blooddonor.di.DatabaseInfo;
import com.sq.sohel.blooddonor.di.PreferenceInfo;
import com.sq.sohel.blooddonor.utils.AppConstants;
import com.sq.sohel.blooddonor.utils.rx.AppSchedulerProvider;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

@Module
public class AppModule {

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return BuildConfig.CLIENT_SECRET;
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@DatabaseInfo String dbName, Context context) {
        AppDatabase appDatabase = null;
        try {
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, dbName).fallbackToDestructiveMigration()
                    .build();
        } catch (Exception ex) {

        }
        return appDatabase;

    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/ubuntu-font-all/Ubuntu-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    ApiHeader.ProtectedApiHeader provideProtectedApiHeader(@ApiInfo String apiKey,
                                                           PreferencesHelper preferencesHelper) {
        return new ApiHeader.ProtectedApiHeader(
                apiKey,
                preferencesHelper.getCurrentUserId(),
                preferencesHelper.getAccessToken());
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }
}
