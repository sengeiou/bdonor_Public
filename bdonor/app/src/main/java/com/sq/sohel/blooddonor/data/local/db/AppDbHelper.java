package com.sq.sohel.blooddonor.data.local.db;

import com.sq.sohel.blooddonor.data.model.db.Donor;
import com.sq.sohel.blooddonor.data.model.db.RequestBlood;
import com.sq.sohel.blooddonor.data.model.db.User;
import com.sq.sohel.blooddonor.utils.StringUtils.StringExtension;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;


@Singleton
public class AppDbHelper implements DbHelper {

    private final AppDatabase mAppDatabase;

    @Inject
    public AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    @Override
    public Observable<List<Donor>> getAllDonors() {
        return Observable.fromCallable(() -> mAppDatabase.donorDao().loadAll());
    }

    @Override
    public Observable<List<RequestBlood>> getAllRequestForBloodData(String bloodType, String email) {
        if (StringExtension.isNullOrWhiteSpace(email)) {
            return Observable.fromCallable(() -> mAppDatabase.requestBloodDao().loadAllByBloodType(bloodType));
        } else {
            return Observable.fromCallable(() -> mAppDatabase.requestBloodDao().loadAllByBloodTypeByEmail(bloodType, email));
        }
    }

    @Override
    public Observable<Donor> getDonorByEmail(final String email) {
        return Observable.fromCallable(() -> mAppDatabase.donorDao().findByEmail(email));
    }

    @Override
    public Observable<Donor> getDonorById(String id) {
        return Observable.fromCallable(() -> mAppDatabase.donorDao().findById(id));
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        return Observable.fromCallable(() -> mAppDatabase.userDao().loadAll());
    }


    @Override
    public Observable<Boolean> insertUser(final User user) {
        return Observable.fromCallable(() -> {
            mAppDatabase.userDao().insert(user);
            return true;
        });
    }

    @Override
    public Observable<User> getUserFromDBByEmail(String email) {
        return Observable.fromCallable(() -> mAppDatabase.userDao().findByEmail(email));
    }

    @Override
    public Observable<Boolean> isDonorEmpty() {
        return Observable.fromCallable(() -> mAppDatabase.donorDao().loadAll().isEmpty());
    }

    @Override
    public Observable<Boolean> isBloodRequestEmpty() {
        return Observable.fromCallable(() -> mAppDatabase.requestBloodDao().loadAllByBloodType("A+").isEmpty());
    }

    @Override
    public Observable<Boolean> saveDonorList(final List<Donor> donorList) {
        return Observable.fromCallable(() -> {
            mAppDatabase.donorDao().insertAll(donorList);
            return true;
        });
    }

    @Override
    public Observable<Boolean> saveRequestBloodList(List<RequestBlood> requestBloodList) {
        return Observable.fromCallable(() -> {
            mAppDatabase.requestBloodDao().insertAll(requestBloodList);
            return true;
        });
    }

    @Override
    public Observable<Boolean> saveDonor(final Donor donor) {
        return Observable.fromCallable(() -> {
            mAppDatabase.donorDao().insert(donor);
            return true;
        });
    }

    @Override
    public Observable<Boolean> saveRequestBlood(RequestBlood requestBlood) {
        return Observable.fromCallable(() -> {
            mAppDatabase.requestBloodDao().insert(requestBlood);
            return true;
        });
    }

    @Override
    public Observable<Boolean> updateDonor(Donor donor) {
        return saveDonor(donor);
    }
}
