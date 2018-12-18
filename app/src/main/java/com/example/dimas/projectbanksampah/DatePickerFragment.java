/*
    Ditulis oleh Ikhwanul Murtadlo
    Editor: Android Studio
    Compiler dan lib yang digunakan: Android Studio,
                  JRE 1.8.0_152-release-1024-b02 amd64
                  JVM OpenJDK 64-Bit Server VM by JetBrains.s.r.o
    Versi dan Upgrade History: 3.1.4
    Tanggal pembuatan software: 24 Juli 2018
    Deskripsi singkat tentang software: Android Studio adalah Integrated Development Enviroment (IDE) untuk sistem operasi Android
*/

package com.example.dimas.projectbanksampah;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.app.DialogFragment;
import android.app.Dialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.widget.TimePicker;


public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog date =  new DatePickerDialog(getActivity(), R.style.DialogTheme,this, year, month, day);
        date.getDatePicker().setMinDate(c.getTimeInMillis());
        return date;
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(c.getTime());
        EditText tv = (EditText) getActivity().findViewById(R.id.tanggalEdit);
        tv.setText(formattedDate);
    }
}
