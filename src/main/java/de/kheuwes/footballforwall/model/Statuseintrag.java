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
    public int minute = -1;
    public String typ = "";
    public String name = "";
    public String bemerkung = "";
    
    @Override
    public String toString() {
        return "Statuseintrag [id=" + id + ", minute=" + minute + ", typ=" + typ + ", name=" + name + ", bemerkung="
                + bemerkung + "]";
    }

    public Statuseintrag() {
    }

    public Statuseintrag(int minute, String typ, String name, String bemerkung) {
        this.minute = minute;
        this.typ = typ;
        this.name = name;
        this.bemerkung = bemerkung;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getMinute() {
        return minute;
    }
    public void setMinute(int minute) {
        this.minute = minute;
    }
    public String getTyp() {
        return typ;
    }
    public void setTyp(String typ) {
        this.typ = typ;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBemerkung() {
        return bemerkung;
    }
    public void setBemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
    }

}
