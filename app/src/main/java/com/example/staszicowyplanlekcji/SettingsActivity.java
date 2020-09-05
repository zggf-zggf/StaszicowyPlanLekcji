package com.example.staszicowyplanlekcji;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class SettingsActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener{

    int classNumber;
    Spinner wybor_klasy;
    Calendar calendar;
    Button przyc;
    List<String> nazwy_klas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        System.out.println("Opened settings");
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);

        przyc = findViewById(R.id.guzior);
        wybor_klasy = findViewById(R.id.wyborKlasy);
        SharedPreferencesManager spm = new SharedPreferencesManager(this);
        classNumber = spm.getWybrana();
        getKlasy();
        updateSpinner();

        przyc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        classNumber = position;
        SharedPreferencesManager spm = new SharedPreferencesManager(this);
        spm.setWyb(position);
        System.out.println(position);
    }

    private void updateSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nazwy_klas);
        wybor_klasy.setAdapter(adapter);
        wybor_klasy.setOnItemSelectedListener(this);
        wybor_klasy.setSelection(classNumber);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        newCalendar.set(Calendar.MONTH, month);
        newCalendar.set(Calendar.YEAR, year);
        System.out.println(newCalendar.get(Calendar.DAY_OF_WEEK));
        if(newCalendar.get(Calendar.DAY_OF_WEEK)==1||newCalendar.get(Calendar.DAY_OF_WEEK)==7){
            Toast.makeText(getApplicationContext(), "w weekend nie ma lekcji :)", Toast.LENGTH_LONG).show();
        }else{
            calendar = newCalendar;
            updateDate(calendar);
        }
    }

    void getKlasy(){
        SharedPreferencesManager spm = new SharedPreferencesManager(this);
        String s = spm.getLekcje();
        System.out.println(s);
        nazwy_klas = Arrays.asList(s.split("-"));
        updateSpinner();
    }

    void updateDate(Calendar c){
        SharedPreferencesManager spm = new SharedPreferencesManager(this);
        spm.setDay(c.get(Calendar.DAY_OF_MONTH));
        spm.setMonth(c.get(Calendar.MONTH));
        spm.setYear(c.get(Calendar.YEAR));
    }
}
