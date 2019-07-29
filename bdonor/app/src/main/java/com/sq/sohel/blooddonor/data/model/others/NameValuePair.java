package com.sq.sohel.blooddonor.data.model.others;

import android.view.View;

import com.sq.sohel.blooddonor.utils.StringUtils.StringExtension;

import java.io.Serializable;

public class NameValuePair implements Cloneable, Serializable {
    private final String mName;
    private final String mValue;

    private final Integer mSortOrder;

    public NameValuePair(final String name, final String value, Integer sortOrder) {
        super();
        if (StringExtension.isNullOrWhiteSpace(name)) {
            throw new IllegalArgumentException("Name may not be null");
        }
        if (StringExtension.isNullOrWhiteSpace(value)) {
            throw new IllegalArgumentException("Value may not be null");
        }


        this.mName = name;
        this.mValue = value;

        this.mSortOrder = sortOrder;
    }
    public NameValuePair(final String name, final String value) {
        super();
        if (StringExtension.isNullOrWhiteSpace(name)) {
            throw new IllegalArgumentException("Name may not be null");
        }
        if (StringExtension.isNullOrWhiteSpace(value)) {
            throw new IllegalArgumentException("Value may not be null");
        }


        this.mName = name;
        this.mValue = value;
        this.mSortOrder = 0;
    }

    public String getName() {
        return mName;
    }

    public String getValue() {
        return mValue;
    }


    public int getSortOrder() {
        return mSortOrder;
    }

    public boolean equals(final Object object) {
        if (object == null) return false;
        if (this == object) return true;
        if (object instanceof NameValuePair) {
            NameValuePair that = (NameValuePair) object;
            return this.mName.equals(that.mName) && this.mValue.equals(that.mValue) && this.mSortOrder.equals(that.mSortOrder);
        } else {
            return false;
        }
    }
}
