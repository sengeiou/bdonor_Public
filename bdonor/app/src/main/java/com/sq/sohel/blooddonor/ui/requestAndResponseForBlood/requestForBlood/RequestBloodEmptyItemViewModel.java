package com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.requestForBlood;



public class RequestBloodEmptyItemViewModel {

    private RequestBloodEmptyItemViewModelListener mListener;

    public RequestBloodEmptyItemViewModel(RequestBloodEmptyItemViewModelListener listener) {
        this.mListener = listener;
    }

    public void onRetryClick() {
        mListener.onRetryClick();
    }

    public interface RequestBloodEmptyItemViewModelListener {

        void onRetryClick();
    }
}
