package com.example.staszicowyplanlekcji;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener)getActivity(), year, month, day);
        Calendar pocz = Calendar.getInstance();
        pocz.set(Calendar.DAY_OF_MONTH, 1);
        pocz.set(Calendar.MONTH, 8);
        pocz.set(Calendar.YEAR, 2020);
        dpd.getDatePicker().setMinDate(pocz.getTimeInMillis());
        return dpd;
    }
}
