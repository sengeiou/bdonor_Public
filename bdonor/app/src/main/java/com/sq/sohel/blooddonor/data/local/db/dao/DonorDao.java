
package com.sq.sohel.blooddonor.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import com.sq.sohel.blooddonor.data.model.db.Donor;
import com.sq.sohel.blooddonor.data.model.db.RequestBlood;

import java.util.List;


@Dao
public interface DonorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Donor donor);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Donor> donors);

    @Query("SELECT * FROM donors")
    List<Donor> loadAll();

    @Query("SELECT * FROM donors WHERE Email LIKE :email LIMIT 1")
    Donor findByEmail(String email);
    @Query("SELECT * FROM donors WHERE id LIKE :id LIMIT 1")
    Donor findById(String id);

//    @Query("Update donors Set isSyncWithServer=1, DonorId_ServerSite = :donorIdServerSite WHERE Id = :Id")
//    Donor UpdateDonorServerId(String donorIdServerSite, String Id);
}
