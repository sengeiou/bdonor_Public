
package com.sq.sohel.blooddonor.ui.dialogMessage;


import android.databinding.ObservableField;

import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.ui.base.BaseViewModel;
import com.sq.sohel.blooddonor.ui.main.rating.RateUsCallback;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

public class DialogViewModel extends BaseViewModel<DialogCallback> {




    public DialogViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public ObservableField<String> getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message.set(message);
    }
    public ObservableField<String> getImageUrl() {

        return imageUrl;
    }
    public ObservableField<byte[]> getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData.set(imageData);
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl.set(imageUrl);
    }
    private ObservableField<String> message = new ObservableField<>();
    private ObservableField<String> imageUrl;
    private ObservableField<byte[]> imageData;

    public void onSubmitClick() {
        getNavigator().dismissDialog();
    }
}
