package com.sq.sohel.blooddonor.ui.requestAndResponseForBlood;

import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.data.model.others.ValidationModel;
import com.sq.sohel.blooddonor.ui.base.BaseViewModel;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

public class RequestAndResponseForBloodViewModel extends BaseViewModel {
    public RequestAndResponseForBloodViewModel(DataManager dataManager,
                                               SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
