package com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood.requestBloodDialogDetails;

import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.data.model.others.ValidationModel;
import com.sq.sohel.blooddonor.ui.base.BaseViewModel;
import com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood.RequestBloodItemViewModel;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

public class RequestBloodDialogViewModel  extends BaseViewModel<RequestBloodDetailsDialogCallback> {

    private RequestBloodItemViewModel requestBloodItemViewModel;

    public RequestBloodItemViewModel getRequestBloodItemViewModel() {
        return requestBloodItemViewModel;
    }

    public void setRequestBloodItemViewModel(RequestBloodItemViewModel requestBloodItemViewModel) {
        this.requestBloodItemViewModel = requestBloodItemViewModel;
    }

    public RequestBloodDialogViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onOkClick(){
        getNavigator().dismissDialog();
    }

}


