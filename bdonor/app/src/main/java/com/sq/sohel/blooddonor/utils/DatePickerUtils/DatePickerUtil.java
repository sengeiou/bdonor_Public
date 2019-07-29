package com.sq.sohel.blooddonor.utils.DatePickerUtils;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.sq.sohel.blooddonor.utils.StringUtils.StringExtension;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.sq.sohel.blooddonor.utils.AppConstants.DATE_FORMAT;
import static com.sq.sohel.blooddonor.utils.AppConstants.DISPLAY_DATE_FORMAT;

public final class DatePickerUtil {

    private DatePickerUtil() {

    }

    //private static DateFormat dateFormat = DateFormat.getDateInstance(DATE_FORMAT, Locale.getDefault());
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DISPLAY_DATE_FORMAT, Locale.getDefault());

    public static void setDatePicker(EditText edittext, String defaultDate, boolean isDisableFutureDate,  boolean isDisablePreviousDate) {

        DatePickerDialog.OnDateSetListener onDateSetListener = (viewDatePicker, year, monthOfYear, dayOfMonth) -> {
            Calendar tempCalendar = Calendar.getInstance();
            tempCalendar.set(Calendar.YEAR, year);
            tempCalendar.set(Calendar.MONTH, monthOfYear);
            tempCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            edittext.setText(simpleDateFormat.format(tempCalendar.getTime()));
        };


        edittext.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog;
            Calendar myCalendar = Calendar.getInstance();

            if (!StringExtension.isNullOrWhiteSpace(defaultDate)) {
                try {
                    Calendar defaultCalendar = Calendar.getInstance();
                    defaultCalendar.setTime(simpleDateFormat.parse(defaultDate));
                    datePickerDialog = new DatePickerDialog(edittext.getContext(), onDateSetListener, defaultCalendar.get(Calendar.YEAR), defaultCalendar.get(Calendar.MONTH), defaultCalendar.get(Calendar.DATE));
                    if (isDisableFutureDate) {
                        datePickerDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
                    }
                    if (isDisablePreviousDate) {
                        datePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
                    }
                    datePickerDialog.show();
                } catch (ParseException e) {

                }
            } else {
                datePickerDialog = new DatePickerDialog(edittext.getContext(), onDateSetListener, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DATE));
                if (isDisableFutureDate) {
                    datePickerDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
                }
                if (isDisablePreviousDate) {
                    datePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
                }
                datePickerDialog.show();
            }
        });
    }

    public static void setDatePicker(EditText edittext, String defaultDate, int showYearLess) {

        DatePickerDialog.OnDateSetListener onDateSetListener = (viewDatePicker, year, monthOfYear, dayOfMonth) -> {
            Calendar tempCalendar = Calendar.getInstance();
            tempCalendar.set(Calendar.YEAR, year);
            tempCalendar.set(Calendar.MONTH, monthOfYear);
            tempCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            edittext.setText(simpleDateFormat.format(tempCalendar.getTime()));
        };

        edittext.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog;
            Calendar myCalendar = Calendar.getInstance();
            myCalendar.add(Calendar.YEAR, -showYearLess);
            if (!StringExtension.isNullOrWhiteSpace(defaultDate)) {
                try {
                    Calendar defaultCalendar = Calendar.getInstance();
                    defaultCalendar.setTime(simpleDateFormat.parse(defaultDate));
                    datePickerDialog = new DatePickerDialog(edittext.getContext(), onDateSetListener, defaultCalendar.get(Calendar.YEAR), defaultCalendar.get(Calendar.MONTH), defaultCalendar.get(Calendar.DATE));
                    datePickerDialog.getDatePicker().setMaxDate((myCalendar.getTimeInMillis()));
                    datePickerDialog.show();
                } catch (ParseException e) {

                }
            } else {
                datePickerDialog = new DatePickerDialog(edittext.getContext(), onDateSetListener, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DATE));
                datePickerDialog.getDatePicker().setMaxDate((myCalendar.getTimeInMillis()));
                datePickerDialog.show();
            }
        });
    }
}
