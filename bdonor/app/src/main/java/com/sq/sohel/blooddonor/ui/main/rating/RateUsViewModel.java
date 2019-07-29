
package com.sq.sohel.blooddonor.ui.main.rating;


import android.databinding.ObservableField;

import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.data.model.api.RateUs.RateUsRequest;
import com.sq.sohel.blooddonor.ui.base.BaseViewModel;
import com.sq.sohel.blooddonor.data.model.others.ValidationModel;
import com.sq.sohel.blooddonor.utils.AppUtils;
import com.sq.sohel.blooddonor.utils.StringUtils.StringExtension;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

import static com.sq.sohel.blooddonor.utils.ObservableFieldUtils.ObservableFieldExtension.getIntegerFromObservableField;
import static com.sq.sohel.blooddonor.utils.ObservableFieldUtils.ObservableFieldExtension.getStringFromObservableField;

public class RateUsViewModel extends BaseViewModel<RateUsCallback> {

    public RateUsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    private ObservableField<String> comments = new ObservableField<>();
    private ObservableField<Integer> rating = new ObservableField<>(5);


    public ObservableField<String> getComments() {
        return comments;
    }

    public void setComments(ObservableField<String> comments) {
        this.comments = comments;
    }

    public ObservableField<Integer> getRating() {
        return rating;
    }

    public void setRating(ObservableField<Integer> rating) {
        this.rating = rating;
    }


    public void onLaterClick() {
        getNavigator().closeMe();
    }

    public void onSubmitClick() {
        int rating = getIntegerFromObservableField(this.rating);
        String comment = getStringFromObservableField(this.comments);

        if (rating < 3 && StringExtension.isNullOrWhiteSpace(comment)) {
            getNavigator().handleError(new Throwable("Please tell us how we can improve."));
            return;
        }
        if (getNavigator().isNetworkConnected()) {
            saveRating();
        } else {
            getNavigator().handleNoNetworkConnection();
        }
    }

    private void saveRating() {
        setIsLoading(true);
        RateUsRequest rateUsRequest = new RateUsRequest();
        rateUsRequest.comment = getStringFromObservableField(this.getComments());
        rateUsRequest.rating = getIntegerFromObservableField(this.rating);
        //rateUsRequest.userId=
        getCompositeDisposable().add(
                getDataManager().postRateUs(rateUsRequest).doOnSuccess(response -> {
                    getNavigator().ShowMessage("Thank you for your feedback", AppUtils.MessageType.THANK_YOU);
                    getNavigator().closeMe();
                }).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                                    setIsLoading(false);
                                }
                                , throwable -> {
                                    setIsLoading(false);
                                    getNavigator().handleNoNetworkConnection();
                                    getNavigator().handleError(throwable);
                                }));
    }
}
