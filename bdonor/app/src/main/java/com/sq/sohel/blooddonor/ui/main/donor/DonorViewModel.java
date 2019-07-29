package com.sq.sohel.blooddonor.ui.main.donor;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.data.model.others.DonorCardData;
import com.sq.sohel.blooddonor.ui.base.BaseViewModel;
import com.sq.sohel.blooddonor.data.model.others.ValidationModel;
import com.sq.sohel.blooddonor.ui.base.IBaseNavigator;
import com.sq.sohel.blooddonor.ui.becomeDonor.becomeHero.HeroNavigator;
import com.sq.sohel.blooddonor.ui.becomeDonor.becomeHero.HeroViewModel;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

import java.util.List;


public class DonorViewModel extends BaseViewModel<DonorNavigator> {

    public final ObservableList<DonorCardData> donorObservableArrayList = new ObservableArrayList<>();

    private final MutableLiveData<List<DonorCardData>> donorListLiveData;

    private HeroViewModel mHeroViewModel;

    public DonorViewModel(DataManager dataManager,
                          SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        donorListLiveData = new MutableLiveData<>();
        mHeroViewModel = new HeroViewModel(dataManager, schedulerProvider);
        fetchDonors();
    }

    public void DonorSync(String donorId) {
        if (getNavigator().isNetworkConnected()) {
            setIsLoading(true);
            getCompositeDisposable().add(getDataManager()
                    .getDonorById(donorId)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(d -> {
                        if (d != null) {
                            //if(mHeroViewModel.getNavigator()!=null){
                                mHeroViewModel.setNavigator((HeroNavigator) getNavigator());
                            //}
                            mHeroViewModel.saveDonor(d);
                        }
                        setIsLoading(false);
                    }, throwable -> {
                        setIsLoading(false);
                        getNavigator().handleError(throwable);
                    }));
        } else {
            getNavigator().handleNoNetworkConnection();
        }
    }

    public void addDonorItemsToList(List<DonorCardData> donors) {
        donorObservableArrayList.clear();
        donorObservableArrayList.addAll(donors);
    }

    public void fetchDonors() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getDonorCardData()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(d -> {
                    if (d != null) {
                        //action = ACTION_ADD_ALL;
                        donorListLiveData.setValue(d);
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public MutableLiveData<List<DonorCardData>> getDonorListLiveData() {
        return donorListLiveData;
    }

    public void onClearFilter() {
        getNavigator().refreshDonorList();
    }

    public void onRedefineSearch() {
        getNavigator().onRedefineSearch();
    }
}
