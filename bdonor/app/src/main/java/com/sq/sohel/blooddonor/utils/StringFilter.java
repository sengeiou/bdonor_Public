//package com.sq.sohel.blooddonor.utils;
//
//import android.text.InputFilter;
//import android.text.SpannableString;
//import android.text.Spanned;
//import android.text.TextUtils;
//import android.widget.EditText;
//
//
//import com.sq.sohel.blooddonor.data.model.others.ValidationModel;
//
//import java.util.*;
//import java.util.ArrayList;
//
//public class StringFilter implements InputFilter {
//
//    private String[] mRangeString;
//    private List<ValidationModel> validationModels = new ArrayList<>();
//    EditText mEditText;
//
//    public StringFilter(String[] rangeString, EditText asbt) {
//        mEditText = asbt;
//        this.mRangeString = rangeString;
//        ValidationModel n = null;
//        for (String s : this.mRangeString) {
//            n = new ValidationModel(s, String.valueOf(s.length()));
//            validationModels.add(n);
//        }
//
//        Collections.sort(validationModels, (o1, o2) -> Integer.compare(Integer.valueOf(o1.getValue()), Integer.valueOf(o2.getValue())));
//    }
//
//    @Override
//    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//        try {
////            int minCharLength = Integer.valueOf(validationModels.get(0).getValue());
////            int maxCharLength = Integer.valueOf(validationModels.get(validationModels.size() - 1).getValue());
////
////            String d = source.toString().substring(start, end);
////            String input = dest.toString() + source.toString();
////            if (input.length() >= maxCharLength) {
////                source = "";
////                mEditText.setText(source);
////                return "";
////
////            }
////
////            if (input.length() >= minCharLength) {
////                if (CommonUtils.contains2(mRangeString, input.toUpperCase())) {
////                    return null;
////                } else {
////                    if (Arrays.toString(mRangeString).contains(input.toUpperCase())) {
////                        return source;
////                    }
////                    source = "";
////                    mEditText.setText(source);
////                    return "";
////                }
////            }
//
//            boolean keepOriginal = true;
//            StringBuilder sb = new StringBuilder(end - start);
//            for (int i = start; i < end; i++) {
//                char c = source.charAt(i);
//                if (isCharAllowed(c)) // put your condition here
//                    sb.append(c);
//                else
//                    keepOriginal = false;
//            }
//            if (keepOriginal)
//                return null;
//            else {
//                if (source instanceof Spanned) {
//                    SpannableString sp = new SpannableString(sb);
//                    TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
//                    return sp;
//                } else {
//                    return sb;
//                }
//            }
//
//        } catch (Exception e) {
//        }
//        source = "";
//        mEditText.setText(source);
//        return "";
//    }
//    private boolean isCharAllowed(char c) {
//        return Arrays.toString(mRangeString).contains(String.valueOf(c)); //Character.isLetterOrDigit(c) || Character.isSpaceChar(c);
//    }
//}
