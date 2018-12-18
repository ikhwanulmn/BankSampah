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

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TextView;
import android.app.DialogFragment;
import android.app.Dialog;
import java.util.Calendar;
import android.widget.TimePicker;


public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the current time as the default values for the time picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //Create and return a new instance of TimePickerDialog
        return new TimePickerDialog(getActivity(), R.style.DialogTheme,this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }
    //onTimeSet() callback method
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        //Do something with the user chosen time
        //Get reference of host activity (XML Layout File) TextView widget
        EditText tv = (EditText) getActivity().findViewById(R.id.waktuEdit);
        //Set a message for user
        //Display the user changed time on TextView
        tv.setText(String.valueOf(hourOfDay) + " : " + String.valueOf(minute));
    }
}
