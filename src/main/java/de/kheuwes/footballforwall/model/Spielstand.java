package de.kheuwes.footballforwall.model;

public class Spielstand {

    //private Long id;
    private int heim;
    private int gast;
    private String hg;
    private int tsNummer;
    private String spielername;

    //private String statusKz = "";
    // Constructors, getters, and setters
    public Spielstand() {}

    public Spielstand(int heim, int gast) {
        this.heim = heim;
        this.gast = gast;
    }

    public Spielstand(int heim, int gast, String hg, int tsNummer) {
        this.heim = heim;
        this.gast = gast;
        this.hg = hg;
        this.tsNummer = tsNummer;
    }

    public int getHeim() {
        return heim;
    }

    public void setHeim(int heim) {
        this.heim = heim;
    }

    public int getGast() {
        return gast;
    }

    public void setGast(int gast) {
        this.gast = gast;
    }

    public String getHg() {
        return hg;
    }

    public void setHg(String hg) {
        this.hg = hg;
    }

    public int getTsNummer() {
        return tsNummer;
    }

    public void setTsNummer(int tsNummer) {
        this.tsNummer = tsNummer;
    }

 @Override
 public String toString() {
     return "Spielstand [id=" + 0 + ", heim=" + heim + ", gast=" + gast 
     + ", hg=" + hg + ", tsNummer=" + tsNummer+ ", spielername=" + spielername        + "]";
 }

 public String shortString() {
     return String.format("%s|%s|%s|%s|%s", this.heim, this.gast, this.hg, this.tsNummer, this.spielername);
 }
    
 public String getSpielername() {
    return spielername;
}

public void setSpielername(String spielername) {
    this.spielername = spielername;
}
}
