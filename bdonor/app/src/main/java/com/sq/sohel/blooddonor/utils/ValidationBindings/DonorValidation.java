package com.sq.sohel.blooddonor.utils.ValidationBindings;

import android.databinding.BindingAdapter;
import android.support.annotation.StringRes;
import android.widget.EditText;

import com.sq.sohel.blooddonor.ui.base.BaseActivity;
import com.sq.sohel.blooddonor.ui.base.BaseViewModel;
import com.sq.sohel.blooddonor.data.model.others.ValidationModel;
import com.sq.sohel.blooddonor.utils.AppUtils;
import com.sq.sohel.blooddonor.utils.StringUtils.StringExtension;

public final class DonorValidation {
    private DonorValidation() {
    }


    @BindingAdapter({"validationKey", "validateValue", "validationMessage", "validationMessageOrder"})
    public static void setError(EditText editText, String validationKey, String validateValue, @StringRes int layourId, String validationMessageOrder) {
        try {

            BaseActivity activity = ((BaseActivity) editText.getContext());
            BaseViewModel viewModel = null;
            if (activity.getPagerAdapter() != null && activity.getPagerAdapter().getActiveFragment() != null) {
                viewModel = activity.getPagerAdapter().getActiveFragment().getViewModel();
            } else {
                viewModel = activity.getViewModel();
            }


            String err = (String) editText.getContext().getResources().getText(layourId);
            ValidationModel validationModel = new ValidationModel(validationKey, err, editText, Integer.valueOf(validationMessageOrder));
            if (StringExtension.isNullOrWhiteSpace(validateValue)) {
                //editText.setError(err);
                viewModel.setViewModelError(validationModel);
            } else {
                editText.setError(null);
                viewModel.removeViewModelError(validationModel);
            }

            //editText.addTextChangedListener(new MyTextWatcher(editText));
        } catch (Exception ex) {
            if (ex != null) {

            }
        }
    }
//    @BindingAdapter({"validationViewType", "validationKey", "validateValue", "validationMessage","validationMessageOrder"})
//    public static void setError(EditText editText, AppUtils.ValidationViewType validationViewType, String validationKey, String validateValue, @StringRes int layourId, int validationMessageOrder) {
//        try {
//
//            BaseActivity activity = ((BaseActivity) editText.getContext());
//
//            BaseViewModel viewModel = activity.getViewModel(); activity.getPagerAdapter().getActiveFragment().getViewModel();
//            String err = (String) editText.getContext().getResources().getText(layourId);
//            ValidationModel validationModel = new ValidationModel(validationKey, err,editText, Integer.valueOf(validationMessageOrder));
//            if (StringExtension.isNullOrWhiteSpace(validateValue)) {
//                //editText.setError(err);
//                viewModel.setViewModelError(validationModel);
//            } else {
//                editText.setError(null);
//                viewModel.removeViewModelError(validationModel);
//            }
//
//            //editText.addTextChangedListener(new MyTextWatcher(editText));
//        } catch (Exception ex) {
//            if (ex != null) {
//
//            }
//        }
//    }

//    @BindingAdapter({"app:validate", "app:validationMessage"})
//    public static void setError(EditText editText, String validate, String validationMessage) {
//        if (StringExtension.isNullOrWhiteSpace(validate)) {
//            editText.setError(validationMessage);
//        } else {
//            editText.setError(null);
//        }
//    }
}


