package com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.data.model.others.RequestBloodCardData;
import com.sq.sohel.blooddonor.data.model.others.ValidationModel;
import com.sq.sohel.blooddonor.ui.base.BaseViewModel;
import com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood.RequestBloodNavigator;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

import java.util.List;


public class RequestBloodViewModel extends BaseViewModel<RequestBloodNavigator> {

    private ObservableList<RequestBloodCardData> requestBloodObservableArrayList = new ObservableArrayList<>();

    public ObservableList<RequestBloodCardData> getRequestBloodObservableArrayList() {
        return requestBloodObservableArrayList;
    }

    public void setRequestBloodObservableArrayList(ObservableList<RequestBloodCardData> requestBloodObservableArrayList) {
        this.requestBloodObservableArrayList = requestBloodObservableArrayList;
    }

    private final MutableLiveData<List<RequestBloodCardData>> requestBloodListLiveData;

    public RequestBloodViewModel(DataManager dataManager,
                                 SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        requestBloodListLiveData = new MutableLiveData<>();
        //fetchRequestBloods();
    }

    public void addRequestBloodItemsToList(List<RequestBloodCardData> requestBloods) {
        requestBloodObservableArrayList.clear();
        requestBloodObservableArrayList.addAll(requestBloods);
    }

    public void fetchRequestBloods(String bloodType, boolean showOnlyMyRequest) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getRequestBloodCardData(bloodType, showOnlyMyRequest)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(d -> {
                    if (d != null) {
                        //action = ACTION_ADD_ALL;

                        requestBloodListLiveData.setValue(d);
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public MutableLiveData<List<RequestBloodCardData>> getRequestBloodListLiveData() {
        return requestBloodListLiveData;
    }


}
