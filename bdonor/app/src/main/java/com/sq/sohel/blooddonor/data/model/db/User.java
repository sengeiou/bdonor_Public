

package com.sq.sohel.blooddonor.data.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Entity(tableName = "users")
public class User {

    @PrimaryKey
    @NonNull
    @Expose
    @SerializedName("email")
    @ColumnInfo(name = "email")
    public String email;

    @NonNull
    @Expose
    @SerializedName("userId")
    @ColumnInfo(name = "userId")
    public String userId;

    @NonNull
    @Expose
    @SerializedName("loggedInMode")
    @ColumnInfo(name = "loggedInMode")
    public String loggedInMode;

    @NonNull
    @Expose
    @SerializedName("userName")
    @ColumnInfo(name = "userName")
    public String userName;

    @NonNull
    @Expose
    @SerializedName("accessToken")
    @ColumnInfo(name = "accessToken")
    public String accessToken;


    @Expose
    @SerializedName("profilePicUrl")
    @ColumnInfo(name = "profilePicUrl")
    public String profilePicUrl;

    @Expose
    @SerializedName("profilePicImageData")
    @ColumnInfo(name = "profilePicImageData", typeAffinity = ColumnInfo.BLOB)
    public byte[] profilePicImageData;

}
