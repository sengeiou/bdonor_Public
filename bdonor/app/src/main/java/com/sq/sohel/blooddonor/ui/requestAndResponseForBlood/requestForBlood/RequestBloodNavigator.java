

package com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood;

import com.sq.sohel.blooddonor.data.model.others.RequestBloodCardData;
import com.sq.sohel.blooddonor.data.model.others.ValidationModel;
import com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood.requestBloodDialogDetails.RequestBloodDetailsDialogCallback;

import java.util.List;


public interface RequestBloodNavigator extends RequestBloodDetailsDialogCallback {
    void updateRequestBlood(List<RequestBloodCardData> requestBloodList);
    void refreshRequestBloodList();
}
