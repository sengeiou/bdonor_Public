package com.sq.sohel.blooddonor.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.sq.sohel.blooddonor.data.model.db.Donor;
import com.sq.sohel.blooddonor.data.model.db.RequestBlood;

import java.util.List;

@Dao
public interface RequestBloodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RequestBlood requestBlood);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RequestBlood> requestBlood);

    //@Query("SELECT * FROM requestBlood")
    //List<RequestBlood> loadAll();
    @Query("SELECT * FROM requestBlood WHERE BloodType = :bloodType")
    List<RequestBlood> loadAllByBloodType(String bloodType);

    @Query("SELECT * FROM requestBlood WHERE BloodType = :bloodType and AddedBy=:email ")
    List<RequestBlood> loadAllByBloodTypeByEmail(String bloodType, String email);

//    @Query("SELECT * FROM requestBlood WHERE Email LIKE :email LIMIT 1")
//    Donor findByEmail(String email);
}
