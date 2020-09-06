package com.example.staszicowyplanlekcji;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Switch;
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
    Button exitButton;
    Switch darkmodeCheckbox;
    Switch saleSwitch;
    Switch nauczSwitch;
    Boolean useDarkmode = false;
    List<String> nazwy_klas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        System.out.println("Opened settings");
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);

        exitButton = findViewById(R.id.exitButton);
        przyc = findViewById(R.id.guzior);
        wybor_klasy = findViewById(R.id.wyborKlasy);
        darkmodeCheckbox = findViewById(R.id.darkmodeCheck);
        saleSwitch = findViewById(R.id.SaleCheck);
        nauczSwitch = findViewById(R.id.nauczCheck);
        SharedPreferencesManager spm = new SharedPreferencesManager(this);
        classNumber = spm.getWybrana();
        useDarkmode = spm.getDarkmode();
        saleSwitch.setChecked(spm.getSala());
        nauczSwitch.setChecked(spm.getNaucz());
        darkmodeCheckbox.setChecked(useDarkmode);
        getKlasy();

        przyc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        darkmodeCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                useDarkmode = isChecked;
                SharedPreferencesManager spm = new SharedPreferencesManager(getApplicationContext());
                spm.setDarkmode(isChecked);
                updateTheme();
            }
        });

        saleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferencesManager spm = new SharedPreferencesManager(getApplicationContext());
                spm.setSala(isChecked);
            }
        });

        nauczSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferencesManager spm = new SharedPreferencesManager(getApplicationContext());
                spm.setNaucz(isChecked);
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void updateTheme(){
        System.out.println("theme: "+useDarkmode);
        if(useDarkmode==true) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        }
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, nazwy_klas);
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
