package com.example.staszicowyplanlekcji;

public class TableRow {
    private String godz;
    private String lekcja = "";
    private String zastepstwo = "";
    private String sala;
    private String nauczyciel;

    public TableRow(String godz) {
        this.godz = godz;
        this.lekcja = "";
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getNauczyciel() {
        return nauczyciel;
    }

    public void setNauczyciel(String nauczyciel) {
        this.nauczyciel = nauczyciel;
    }

    public String getGodz() {
        return godz;
    }

    public void setGodz(String godz) {
        this.godz = godz;
    }

    public String getLekcja() {
        return lekcja;
    }

    public void setLekcja(String lekcja) {
        this.lekcja = lekcja;
    }

    public String getZastepstwo() {
        return zastepstwo;
    }

    public void setZastepstwo(String zastepstwo) {
        this.zastepstwo = zastepstwo;
    }
}
