package com.sq.sohel.blooddonor.utils.ValidationBindings;

import android.text.Editable;
import android.widget.EditText;

/**
 * A setText() call in any of the callbacks below will not result in TextWatcher being
 * called again.
 */
public class MyTextWatcher extends EditableTextWatcher {
    private EditText mEditText;

    private MyTextWatcher() {
    }

    public MyTextWatcher(EditText editText) {
        this.mEditText = editText;
    }

    @Override
    protected void beforeTextChange(CharSequence s, int start, int count, int after) {
    }

    @Override
    protected void onTextChange(CharSequence s, int start, int before, int count) {

    }

    @Override
    protected void afterTextChange(Editable s) {
        mEditText.setText(s);
    }
}
