package com.sq.sohel.blooddonor.data.local.db;



import com.sq.sohel.blooddonor.data.model.db.Donor;
//import com.sq.sohel.blooddonor.data.model.db.Option;

import com.sq.sohel.blooddonor.data.model.db.RequestBlood;
import com.sq.sohel.blooddonor.data.model.db.User;

import java.util.List;

import io.reactivex.Observable;



public interface DbHelper {

    Observable<List<Donor>> getAllDonors();
    Observable<List<RequestBlood>> getAllRequestForBloodData(String bloodType, String email);
    Observable<Donor> getDonorByEmail(final String email);
    Observable<Donor> getDonorById(final String id);
    Observable<Boolean> isDonorEmpty();
    Observable<Boolean> isBloodRequestEmpty();
    Observable<Boolean> saveDonorList(List<Donor> donorList);
    Observable<Boolean> saveRequestBloodList(List<RequestBlood> requestBloodList);
    Observable<Boolean> saveDonor(final Donor donor);
    Observable<Boolean> saveRequestBlood(final RequestBlood requestBlood);
    Observable<Boolean> updateDonor(final Donor donor);


    Observable<List<User>> getAllUsers();
    Observable<Boolean> insertUser(final User user);
    Observable<User> getUserFromDBByEmail(final String email);

}
