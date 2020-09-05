package com.example.staszicowyplanlekcji;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int classNumber;
    TextView planik;
    Calendar calendar;
    TextView tekst;
    Button nextButton;
    Button prevButton;
    Button settButton;
    Boolean showSala = true;
    Boolean showNauczyciel = true;
    List<String> nazwy_klas = new ArrayList<>();
    List<TableRow> zajecia = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerViewDefaultAdapter adapter;
    String prevURL = "";
    Document prevDoc = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tekst = findViewById(R.id.dataText);
        calendar = Calendar.getInstance();
        repairDate(calendar);
        nextButton = findViewById(R.id.next);
        prevButton = findViewById(R.id.prev);
        settButton = findViewById(R.id.settingsButton);
        SharedPreferencesManager spm = new SharedPreferencesManager(this);
        classNumber = spm.getWybrana();
        new loadClasses().execute();
        recyclerView = findViewById(R.id.recycler);
        adapter = new RecyclerViewDefaultAdapter(this, zajecia);
        initRecyclerView();


        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementDate(calendar);
                calendarChanged(calendar);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementDate(calendar);
                calendarChanged(calendar);
            }
        });

        settButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate(calendar);
                SharedPreferencesManager spm = new SharedPreferencesManager(MainActivity.this.getApplicationContext());
                spm.setWyb(classNumber);
                startActivityForResult(new Intent(MainActivity.this.getApplication(), SettingsActivity.class), 1);
            }
        });
    }

    public class loadClasses extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect(Constants.PLAN_URL+"lista.html").get();
                //System.out.println(doc.text());
                for(Element klasa : doc.select("ul:contains(3A) > li")){
                    //System.out.println(klasa.text());
                    nazwy_klas.add(klasa.text());
                }
            } catch (IOException e) {
                System.out.println("F");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            saveKlasy();
            calendarChanged(calendar);
        }
    }

    public class loadDay extends AsyncTask<Void, Void, Void>{
        String wynik = "";

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            while(zajecia.get(zajecia.size()-1).getLekcja()=="")zajecia.remove(zajecia.size()-1);
            new loadZastepstwa().execute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            System.out.println("zaczynam");
            try {
                String curURL = Constants.PLAN_URL+"plany/o"+Integer.toString(classNumber+1)+".html";

                if(prevURL!=curURL){
                    prevURL=curURL;
                    prevDoc = Jsoup.connect(curURL).get();
                }

                zajecia.clear();
                for(Element element : prevDoc.select("* .g")){
                    zajecia.add(new TableRow(element.text()));
                    //System.out.println(element.text());
                }
                String qur2 = "br, * .p";
                if(showNauczyciel) qur2+=", * .n";
                if(showSala) qur2+=", * .s";
                for(int i = 0; i < zajecia.size(); i++){
                    TableRow row = zajecia.get(i);
                    row.setLekcja("");
                    String qur = "tr tbody > :eq("+(i+1)+") > :eq("+Integer.toString(calendar.get(Calendar.DAY_OF_WEEK))+")";
                    //System.out.println(qur);
                    for(Element coc1 : prevDoc.select(qur)) {
                        for (Element coc : coc1.select(qur2)) {
                            row.setLekcja(row.getLekcja() + coc.text() + " ");
                            if(coc.is("br")) row.setLekcja(row.getLekcja() + "\n");
                        }
                        //if(row.getLekcja()!="")row.setLekcja(row.getLekcja() + "\n");
                        //System.out.println(Integer.toString(i));
                    }
                    /*if(row.getLekcja()==""){
                        System.out.println("*");
                        for (Element coc : prevDoc.select(qur))
                            for(Element coc1:coc.select(qur2))
                                row.setLekcja(row.getLekcja() + coc1.text() + " ");
                    }*/
                    System.out.println(row.getLekcja()+"----\n");
                }
                for(TableRow row : zajecia){
                    //System.out.println(row.getGodz()+" "+row.getLekcja());
                    wynik+=row.getGodz()+": "+row.getLekcja()+"\n";
                }
            } catch (IOException e) {
                System.out.println("F");
                e.printStackTrace();
            }
            return null;

        }

    }


    private void initRecyclerView(){
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void incrementDate(Calendar c){
        if(c.get(Calendar.DAY_OF_WEEK)==6){
            c.add(Calendar.DATE, 3);
        }else c.add(Calendar.DATE, 1);
    }

    private void decrementDate(Calendar c){
        if(c.get(Calendar.DAY_OF_WEEK)==2){
            c.add(Calendar.DATE, -3);
        }else c.add(Calendar.DATE, -1);
    }
    public void repairDate(Calendar c){
        if(c.get(Calendar.DAY_OF_WEEK)==0){
            c.add(Calendar.DATE, 1);
        }else if(c.get(Calendar.DAY_OF_WEEK)==7){
            c.add(Calendar.DATE, 2);
        }
    }

    private void calendarChanged(Calendar c){
        tekst.setText(nazwy_klas.get(classNumber)+" "+dayParser(c.get(Calendar.DAY_OF_WEEK))+", "+Integer.toString(c.get(Calendar.DAY_OF_MONTH))+"."+Integer.toString(c.get(Calendar.MONTH)+1)+"."+Integer.toString(c.get(Calendar.YEAR)));

        new loadDay().execute();
    }

    public class loadZastepstwa extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            System.out.println("Ladowanie Zastepstw");
            String day, month, year;
            year = Integer.toString(calendar.get(Calendar.YEAR));
            month = Integer.toString(calendar.get(Calendar.MONTH)+1);
            day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
            if(day.length()==1) day = "0"+day;
            if(month.length()==1) month = "0"+month;

            String url = Constants.ZAS_URL+year+"-"+month+"-"+day;

            try {
                Document doc = Jsoup.connect(url).get();
                System.out.println("connected Successfully");
                for(Element row : doc.select("tr")){
                    if(row.is(":contains("+nazwy_klas.get(classNumber)+")")){
                        int godz = Integer.parseInt(row.select(":eq(0)").text());
                        String message = row.select(":eq(2)").text();
                        if(message==""){
                            message = row.select(":eq(1)").text();
                            message = message.substring(message.indexOf('-')+2, message.length());
                        }
                        zajecia.get(godz-1).setZastepstwo(message);
                        System.out.println(godz+": "+zajecia.get(godz-1).getZastepstwo());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    String dayParser(int dayOfTheWeek){
        String out = "";
        switch(dayOfTheWeek){
            case 2:
                out = "Poniedziałek";
                break;
            case 3:
                out = "Wtorek";
                break;
            case 4:
                out = "Środa";
                break;
            case 5:
                out = "Czwartek";
                break;
            case 6:
                out = "Piątek";
                break;
        }
        return out;
    }

    void saveKlasy(){
        SharedPreferencesManager spm = new SharedPreferencesManager(this);

        String parsedKlasy = "";
        for(String s : nazwy_klas){
            parsedKlasy+=s+"-";
        }
        parsedKlasy=parsedKlasy.substring(0, parsedKlasy.length()-1);
        spm.setLekcje(parsedKlasy);
    }

    void updateDate(Calendar c){
        SharedPreferencesManager spm = new SharedPreferencesManager(this);
        spm.setDay(c.get(Calendar.DAY_OF_MONTH));
        spm.setMonth(c.get(Calendar.MONTH));
        spm.setYear(c.get(Calendar.YEAR));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        SharedPreferencesManager spm = new SharedPreferencesManager(this);
        if (requestCode == 1) {
            System.out.println("exited settings");
            calendar.set(Calendar.YEAR, spm.getYear());
            calendar.set(Calendar.DAY_OF_MONTH, spm.getDay());
            calendar.set(Calendar.MONTH, spm.getMonth());
            System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
            System.out.println(calendar.get(Calendar.MONTH));
            System.out.println(calendar.get(Calendar.YEAR));
            classNumber = spm.getWybrana();
            calendarChanged(calendar);
        }
    }//onActivityResult
}
