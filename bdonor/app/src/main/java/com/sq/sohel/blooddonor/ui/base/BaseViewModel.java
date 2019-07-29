package com.sq.sohel.blooddonor.ui.base;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import com.sq.sohel.blooddonor.data.DataManager;
import com.sq.sohel.blooddonor.data.model.others.ValidationModel;
import com.sq.sohel.blooddonor.utils.rx.SchedulerProvider;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel<N> extends ViewModel {

    private final DataManager mDataManager;

    private final ObservableBoolean mIsLoading = new ObservableBoolean(false);

    private final SchedulerProvider mSchedulerProvider;

    private CompositeDisposable mCompositeDisposable;

    private WeakReference<N> mNavigator;

    public List<ValidationModel> getViewModelError() {
        Collections.sort(mViewModelError, (o1, o2) -> Integer.compare(Integer.valueOf(o1.getSortOrder()), Integer.valueOf(o2.getSortOrder())));
        return mViewModelError;
    }

    private List<ValidationModel> mViewModelError;

    public BaseViewModel(DataManager dataManager,
                         SchedulerProvider schedulerProvider) {
        this.mDataManager = dataManager;
        this.mSchedulerProvider = schedulerProvider;
        this.mCompositeDisposable = new CompositeDisposable();
        mViewModelError = new ArrayList<>();
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading.set(isLoading);
    }

    public N getNavigator() {
        if (mNavigator != null) {
            return mNavigator.get();
        } else {
            return null;
        }
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public final void setViewModelError(ValidationModel validationModel) {
        if (!mViewModelError.contains(validationModel)) {
            mViewModelError.add(validationModel);
        }
    }

    public final void removeViewModelError(ValidationModel validationModel) {
        if (mViewModelError.contains(validationModel)) {
            mViewModelError.remove(validationModel);
        }
    }


}


