package com.sq.sohel.blooddonor.data.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


import com.sq.sohel.blooddonor.data.local.db.dao.DonorDao;
import com.sq.sohel.blooddonor.data.local.db.dao.RequestBloodDao;
import com.sq.sohel.blooddonor.data.local.db.dao.UserDao;

import com.sq.sohel.blooddonor.data.model.db.Donor;
import com.sq.sohel.blooddonor.data.model.db.RequestBlood;
import com.sq.sohel.blooddonor.data.model.db.User;

import static com.sq.sohel.blooddonor.utils.AppConstants.DB_VERSION;

@Database(entities = {User.class, Donor.class, RequestBlood.class}, version = DB_VERSION)
public abstract class AppDatabase extends RoomDatabase {

    //public abstract OptionDao optionDao();

    public abstract DonorDao donorDao();

    public abstract RequestBloodDao requestBloodDao();

    public abstract UserDao userDao();
}
