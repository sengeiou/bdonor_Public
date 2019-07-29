package com.sq.sohel.blooddonor.ui.main.donor;



public class DonorEmptyItemViewModel {

    private DonorEmptyItemViewModelListener mListener;

    public DonorEmptyItemViewModel(DonorEmptyItemViewModelListener listener) {
        this.mListener = listener;
    }

    public void onRetryClick() {
        mListener.onRetryClick();
    }

    public interface DonorEmptyItemViewModelListener {

        void onRetryClick();
    }
}
