package com.sq.sohel.blooddonor.ui.main.donor;

import android.databinding.ObservableField;

import com.sq.sohel.blooddonor.data.model.others.DonorCardData;
import com.sq.sohel.blooddonor.utils.AppConstants;


public class DonorItemViewModel {


    public final ObservableField<String> imageUrl;

    public final DonorItemViewModelListener mListener;

    public final ObservableField<String> Id;

    public final ObservableField<String> Name;

    public final ObservableField<String> Email;

    public final ObservableField<String> Gender;

    public final ObservableField<String> ContactNumber;

    public final ObservableField<String> BloodType;
    public final ObservableField<String> FullAddress;

    public final ObservableField<Integer> Age;
    public final ObservableField<String> FullAge;

    private final DonorCardData mDonor;

    public DonorItemViewModel(DonorCardData donor, DonorItemViewModelListener listener) {
        this.mDonor = donor;
        this.mListener = listener;
        imageUrl = new ObservableField<>(AppConstants.DUMMY_IMAGE);

        Id = new ObservableField<>(mDonor.getId());
        Name = new ObservableField<>(mDonor.getName());
        Email = new ObservableField<>(mDonor.getEmail());
        Gender = new ObservableField<>(mDonor.getGender());

        ContactNumber = new ObservableField<>(mDonor.getContactNumber());
        BloodType = new ObservableField<>(mDonor.getBloodType());
        FullAddress = new ObservableField<>(mDonor.getFullAddress());

        Age = new ObservableField<>(mDonor.getAge());
        FullAge = new ObservableField<>(mDonor.getFullAge());
    }

    public void onItemClick() {
        mListener.onItemClick("mBlog.getBlogUrl()");
        //Toast.makeText(getApplicationContext(), "asdasd", Toast.LENGTH_SHORT).show();
    }

    public void onSync() {
        mListener.onSync(mDonor.getId());
        //Toast.makeText(getApplicationContext(), "asdasd", Toast.LENGTH_SHORT).show();
    }
    public void onEdit() {
        mListener.onEdit(mDonor.getEmail());
        //Toast.makeText(getApplicationContext(), "asdasd", Toast.LENGTH_SHORT).show();
    }
    public interface DonorItemViewModelListener {

        void onItemClick(String blogUrl);
        void onSync(String donorId);
        void onEdit(String donorEmail);
    }
}
