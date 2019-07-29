package com.sq.sohel.blooddonor.utils.SpinnerUtils;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.lang.reflect.Field;

import static com.sq.sohel.blooddonor.utils.SpinnerUtils.SpinnerExtension.getSpinnerValueIndex;

public final class SpinnerBinding {

    @InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
    public static String getTextSpinner(Spinner view) {

        if (view.getSelectedItem() != null && (!view.getSelectedItem().toString().isEmpty())) {
            return view.getSelectedItem().toString();
        } else {
            return "";
        }
    }

    @InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
    public static Integer getIntSpinner(Spinner view) {

        if (view.getSelectedItem() != null && (!view.getSelectedItem().toString().isEmpty())) {
            return Integer.valueOf(view.getSelectedItem().toString());
        } else {
            return null;
        }
    }

    @BindingAdapter({"android:textAttrChanged"})
    public static void setTextAttrChangedSpinner(Spinner view, final InverseBindingListener listener) {
        view.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listener.onChange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @BindingAdapter("android:text")
    public static void setTextSpinner(Spinner view, String value) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) view.getAdapter();
        view.setSelection(getSpinnerValueIndex(view, value));
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @BindingAdapter("android:text")
    public static void setIntSpinner(Spinner view, Integer value) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) view.getAdapter();
        view.setSelection(getSpinnerValueIndex(view, String.valueOf(value)));
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @BindingAdapter("entries")
    public static void setEntriesSpinner(Spinner view, String[] value) {
        ArrayAdapter<String> adapterMinPwdLength = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, value);
        adapterMinPwdLength.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        view.setAdapter(adapterMinPwdLength);
        Field popup = null;
        android.widget.ListPopupWindow popupWindow;

        try {
            popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            Object obj = popup.get(view);
            if (obj instanceof android.widget.ListPopupWindow) {
                popupWindow = (android.widget.ListPopupWindow) obj;
                if (value.length > 5) {
                    popupWindow.setHeight(600);
                }
            }

        } catch (NoSuchFieldException e) {
            //e.printStackTrace();
        } catch (IllegalAccessException e) {
            //e.printStackTrace();
        }
    }

//    @BindingAdapter("entries")
//    public static void setIntEntriesSpinner(Spinner view, Integer[] value) {
//        ArrayAdapter<Integer> adapterMinPwdLength = new ArrayAdapter<Integer>(view.getContext(), android.R.layout.simple_spinner_item, value);
//        adapterMinPwdLength.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        view.setAdapter(adapterMinPwdLength);
//        Field popup = null;
//        android.widget.ListPopupWindow popupWindow;
//
//        try {
//            popup = Spinner.class.getDeclaredField("mPopup");
//            popup.setAccessible(true);
//
//            Object obj = popup.get(view);
//            if (obj instanceof android.widget.ListPopupWindow) {
//                popupWindow = (android.widget.ListPopupWindow) obj;
//                if (value.length > 5) {
//                    popupWindow.setHeight(600);
//                }
//            }
//
//        } catch (NoSuchFieldException e) {
//            //e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            //e.printStackTrace();
//        }
//    }


}
