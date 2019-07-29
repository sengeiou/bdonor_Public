package com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood;

import android.databinding.ObservableField;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

import com.sq.sohel.blooddonor.data.model.others.RequestBloodCardData;
import com.sq.sohel.blooddonor.utils.AppConstants;

import java.io.Serializable;


public class RequestBloodItemViewModel implements Serializable {


    public final ObservableField<String> imageUrl;

    public final RequestBloodItemViewModelListener mListener;

    public final ObservableField<String> Id;

    public final ObservableField<String> ContactPerson1;
    public final ObservableField<String> ContactNumber1;

    public final ObservableField<String> ContactPerson2;
    public final ObservableField<String> ContactNumber2;


    public final ObservableField<String> ContactPerson3;
    public final ObservableField<String> ContactNumber3;

    public final ObservableField<String> BloodType;
    public final ObservableField<String> FullAddress;

    public final ObservableField<String> Description;
    public final ObservableField<String> Comments;

    private final RequestBloodCardData mRequestBlood;

    public RequestBloodItemViewModel(RequestBloodCardData requestBlood, RequestBloodItemViewModelListener listener) {
        this.mRequestBlood = requestBlood;
        this.mListener = listener;
        imageUrl = new ObservableField<>(AppConstants.DUMMY_IMAGE);
        Id = new ObservableField<>(mRequestBlood.getId());

        ContactPerson1 = new ObservableField<>(mRequestBlood.getContactPerson1());
        ContactNumber1 = new ObservableField<>(mRequestBlood.getContactNumber1());

        ContactPerson2 = new ObservableField<>(mRequestBlood.getContactPerson2());
        ContactNumber2 = new ObservableField<>(mRequestBlood.getContactNumber2());

        ContactPerson3 = new ObservableField<>(mRequestBlood.getContactPerson3());
        ContactNumber3 = new ObservableField<>(mRequestBlood.getContactNumber3());

        BloodType = new ObservableField<>(mRequestBlood.getBloodType());
        FullAddress = new ObservableField<>(mRequestBlood.getFullAddress());
        Description = new ObservableField<>(mRequestBlood.getDescription());
        Comments = new ObservableField<>(mRequestBlood.getComments());
    }

    public void onItemClick() {
        mListener.onItemClick(this);
    }

    public interface RequestBloodItemViewModelListener {

        void onItemClick(RequestBloodItemViewModel requestBloodItemViewModel);
    }
}
