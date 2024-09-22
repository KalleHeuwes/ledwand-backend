package de.kheuwes.footballforwall.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Statuseintrag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public String typ = "";
    public int spielminute = 0;
    public String hg = "";
    public int rueckennumer = 0;
    public String spielername = "";
    public String zusatz = "";

    public Statuseintrag() {
    }

    public Statuseintrag(String typ, int spielminute, String hg, int rueckennumer, String spielername, String zusatz) {
        this.typ = typ;
        this.spielminute = spielminute;
        this.hg = hg;
        this.rueckennumer = rueckennumer;
        this.spielername = spielername;
        this.zusatz = zusatz;
    }

    @Override
    public String toString() {
        return "Statuseintrag [id=" + id + ", typ=" + typ + ", spielminute=" + spielminute + ", hg=" + hg
                + ", rueckennumer=" + rueckennumer + ", spielername=" + spielername + ", zusatz=" + zusatz + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public int getSpielminute() {
        return spielminute;
    }

    public void setSpielminute(int spielminute) {
        this.spielminute = spielminute;
    }

    public String getHg() {
        return hg;
    }

    public void setHg(String hg) {
        this.hg = hg;
    }

    public int getRueckennumer() {
        return rueckennumer;
    }

    public void setRueckennumer(int rueckennumer) {
        this.rueckennumer = rueckennumer;
    }

    public String getSpielername() {
        return spielername;
    }

    public void setSpielername(String spielername) {
        this.spielername = spielername;
    }

    public String getZusatz() {
        return zusatz;
    }

    public void setZusatz(String zusatz) {
        this.zusatz = zusatz;
    }

    

}