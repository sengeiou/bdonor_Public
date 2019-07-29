///*
// * Copyright (c) 2015 Sergio Rodrigo
// *
// * This software may be modified and distributed under the terms
// * of the MIT license. See the LICENSE file for details.
// */
//package com.sq.sohel.blooddonor.utils.SpinnerUtils;
//
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Spinner;
//
//import java.lang.reflect.Field;
//
///**
// * Provides methods to work with a hint element.
// */
//public class HintSpinner<T> {
//	private static final String TAG = HintSpinner.class.getSimpleName();
//
//	/**
//	 * Used to handle the spinner events.
//	 *
//	 * @param <T> Type of the data used by the spinner
//	 */
//	public interface Callback<T> {
//		/**
//		 * When a spinner item has been selected.
//		 *
//		 * @param position Position selected
//		 * @param itemAtPosition Item selected
//		 */
//		void onItemSelected(int position, T itemAtPosition);
//	}
//
//	private final Spinner spinner;
//	private final HintAdapter<T> adapter;
//	private final Callback<T> callback;
//
//	public HintSpinner(Spinner spinner, HintAdapter<T> adapter, Callback<T> callback) {
//		this.spinner = spinner;
//		this.adapter = adapter;
//		this.callback = callback;
//	}
//
//	/**
//	 * Initializes the hint spinner.
//	 *
//	 * By default, the hint is selected when calling this method.
//	 */
//	public void init() {
//		spinner.setAdapter(adapter);
//		Field popup = null;
//		android.widget.ListPopupWindow popupWindow;
//
//
//		try {
//			popup = Spinner.class.getDeclaredField("mPopup");
//			popup.setAccessible(true);
//
//			Object obj = popup.get(spinner);
//			if (obj instanceof android.widget.ListPopupWindow) {
//				popupWindow = (android.widget.ListPopupWindow) obj;
//				popupWindow.setHeight(600);
//			}
//		} catch (NoSuchFieldException e) {
//			//e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}
//
//		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//			@Override
//			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//				Log.d(TAG, "position selected: " + position);
//				if (HintSpinner.this.callback == null) {
//					throw new IllegalStateException("callback cannot be null");
//				}
//				if (!isHintPosition(position)) {
//					Object item = HintSpinner.this.spinner.getItemAtPosition(position);
//					HintSpinner.this.callback.onItemSelected(position, (T) item);
//				}
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> parent) {
//				Log.d(TAG, "Nothing selected");
//			}
//		});
//		selectHint();
//	}
//
//	private boolean isHintPosition(int position) {
//		return position == adapter.getHintPosition();
//	}
//
//	/**
//	 * Selects the hint element.
//	 */
//	public void selectHint() {
//		spinner.setSelection(adapter.getHintPosition());
//	}
//}
//
