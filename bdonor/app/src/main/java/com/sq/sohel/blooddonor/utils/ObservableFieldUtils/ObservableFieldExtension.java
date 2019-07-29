package com.sq.sohel.blooddonor.utils.ObservableFieldUtils;

import android.databinding.ObservableField;

import java.util.Date;

import javax.annotation.Nonnull;

import static com.sq.sohel.blooddonor.utils.StringUtils.StringExtension.isNullOrWhiteSpace;

public final class ObservableFieldExtension {

    private ObservableFieldExtension() {
    }

    public static String getStringFromObservableField(ObservableField<String> observableField) {
        if (observableField != null && !isNullOrWhiteSpace(observableField.get())) {
            return observableField.get();
        }
        return "";
    }

    public static int getIntegerFromObservableField(ObservableField<Integer> observableField) {
        if (observableField != null & observableField.get() != null) {
            return observableField.get();
        }
        return 0;
    }

    public static Date getDateFromObservableField(ObservableField<Date> observableField) {
        if (observableField != null && observableField.get() != null) {
            return observableField.get();
        }
        return null;
    }

    public static byte[] getByteArrayFromObservableField(ObservableField<byte[]> observableField) {
        if (observableField != null && observableField.get() != null) {
            return observableField.get();
        }
        return null;
    }

    public static boolean getBooleanFromObservableField(ObservableField<Boolean> observableField) {
        if (observableField != null && observableField.get() != null) {
            return observableField.get();
        }
        return false;
    }
}
