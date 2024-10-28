package de.kheuwes.footballforwall.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Spielstand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int heim;

    private int gast;

    private String hg;

    private int tsNummer;

    private String statusKz = "";

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

    public String getStatusKz() {
        return statusKz;
    }

    public void setStatusKz(String statusKz) {
        this.statusKz = statusKz;
    }

    @Override
    public String toString() {
        return "Spielstand [id=" + id + ", heim=" + heim + ", gast=" + gast 
        + ", hg=" + hg + ", tsNummer=" + tsNummer + ", statusKz=" + statusKz
                + "]";
    }
    
}
