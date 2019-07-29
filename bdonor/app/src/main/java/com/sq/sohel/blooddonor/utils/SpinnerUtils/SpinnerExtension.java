package com.sq.sohel.blooddonor.utils.SpinnerUtils;

import android.widget.Spinner;

public final class SpinnerExtension {
    private SpinnerExtension() {
    }
    public static int getSpinnerValueIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }
}
