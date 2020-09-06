package com.example.staszicowyplanlekcji;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.BoringLayout;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesManager {
    Context context;
    final static String KEY_NAME = "default";
    final static String KEY_LEKCJE = "l";
    final static String KEY_NAUCZ = "n";
    final static String KEY_SALA = "s";
    final static String KEY_WYB = "lekcja";
    final static String KEY_DAY = "day";
    final static String KEY_MONTH = "month";
    final static String KEY_YEAR = "year";
    final static String KEY_DARKMODE = "darkmode";

    SharedPreferencesManager(Context c){
        context=c;
    }

    public String getLekcje(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_NAME, MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LEKCJE, "");
    }

    public Boolean getSala(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_NAME, MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_SALA, false);
    }

    public Boolean getNaucz(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_NAME, MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_NAUCZ, false);
    }

    public int getWybrana(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_NAME, MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_WYB, 0);
    }

    public int getDay(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_NAME, MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_DAY, 0);
    }

    public int getMonth(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_NAME, MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_MONTH, 0);
    }

    public int getYear(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_NAME, MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_YEAR, 0);
    }

    public Boolean getDarkmode(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_NAME, MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_DARKMODE, false);
    }

    public void setLekcje(String s){
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_LEKCJE, s);
        editor.apply();
    }

    public void setSala(Boolean b){
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(KEY_SALA, b);
        editor.apply();
    }

    public void setNaucz(Boolean b){
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(KEY_NAUCZ, b);
        editor.apply();
    }

    public void setWyb(int i){
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_WYB, i);
        editor.apply();
    }

    public void setDay(int i){
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_DAY, i);
        editor.apply();
    }

    public void setMonth(int i){
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_MONTH, i);
        editor.apply();
    }

    public void setYear(int i){
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_YEAR, i);
        editor.apply();
    }

    public void setDarkmode(Boolean i){
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(KEY_DARKMODE, i);
        editor.apply();
    }
}
