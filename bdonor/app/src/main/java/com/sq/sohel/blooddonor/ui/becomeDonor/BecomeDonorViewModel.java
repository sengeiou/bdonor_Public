package com.sq.sohel.blooddonor.ui.becomeDonor;


import android.databinding.ObservableField;

import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.ui.base.BaseViewModel;
import com.sq.sohel.blooddonor.data.model.others.ValidationModel;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

public class BecomeDonorViewModel extends BaseViewModel {

    public BecomeDonorViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public ObservableField<String> getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle.set(activityTitle);
    }

    private ObservableField<String> activityTitle = new ObservableField<>();
}
