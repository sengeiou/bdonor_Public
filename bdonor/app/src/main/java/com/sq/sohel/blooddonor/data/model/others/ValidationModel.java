package com.sq.sohel.blooddonor.data.model.others;

import android.view.View;

import com.sq.sohel.blooddonor.utils.StringUtils.StringExtension;

import java.io.Serializable;

public class ValidationModel implements Cloneable, Serializable {
    private final String mKey;
    private final String mValue;
    private final View mControl;
    private final Integer mSortOrder;

    public ValidationModel(final String key, final String value, final View control, Integer sortOrder) {
        super();
        if (StringExtension.isNullOrWhiteSpace(key)) {
            throw new IllegalArgumentException("Key may not be null");
        }
        if (StringExtension.isNullOrWhiteSpace(value)) {
            throw new IllegalArgumentException("Value may not be null");
        }
//        if (control==null) {
//            throw new IllegalArgumentException("Control may not be null");
//        }

        this.mKey = key;
        this.mValue = value;
        this.mControl = control;
        this.mSortOrder = sortOrder == null ? Integer.MAX_VALUE : sortOrder;
    }

    public String getKey() {
        return mKey;
    }

    public String getValue() {
        return mValue;
    }

    public View getControl() {
        return mControl;
    }

    public int getSortOrder() {

        return mSortOrder;
    }

    public boolean equals(final Object object) {
        if (object == null) return false;
        if (this == object) return true;
        if (object instanceof ValidationModel) {
            ValidationModel that = (ValidationModel) object;
            return this.mKey.equals(that.mKey) && this.mValue.equals(that.mValue) && this.mControl.equals(that.mControl) && this.mSortOrder.equals(that.mSortOrder);
        } else {
            return false;
        }
    }
}
