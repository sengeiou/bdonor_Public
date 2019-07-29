package com.sq.sohel.blooddonor.ui.main.donor;

import com.sq.sohel.blooddonor.data.model.others.DonorCardData;
import com.sq.sohel.blooddonor.data.model.others.ValidationModel;
import com.sq.sohel.blooddonor.ui.base.IBaseNavigator;

import java.util.List;


public interface DonorNavigator extends IBaseNavigator {
    void updateDonor(List<DonorCardData> donorList);
    void refreshDonorList();
    void onRedefineSearch();
}
