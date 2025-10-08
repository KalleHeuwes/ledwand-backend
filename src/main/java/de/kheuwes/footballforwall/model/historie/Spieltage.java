package de.kheuwes.footballforwall.model.historie;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "spieltage")
public class Spieltage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String saison;

    private int spieltag;

    private String datum;

    @Column(length = 1)
    private String heimOderAuswaerts; // "H" oder "A"

    private String gegner;

    private String ergebnis;

    private int punkte;

    private int platz;

    @Column(length = 1000)
    private String geschossen; // z. B. "83|Marius Schomaker;69|Jonas Schomaker"

    private int kassiert;

    // --- Getter & Setter ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSaison() {
        return saison;
    }

    public void setSaison(String saison) {
        this.saison = saison;
    }

    public int getSpieltag() {
        return spieltag;
    }

    public void setSpieltag(int spieltag) {
        this.spieltag = spieltag;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getHeimOderAuswaerts() {
        return heimOderAuswaerts;
    }

    public void setHeimOderAuswaerts(String heimOderAuswaerts) {
        this.heimOderAuswaerts = heimOderAuswaerts;
    }

    public String getGegner() {
        return gegner;
    }

    public void setGegner(String gegner) {
        this.gegner = gegner;
    }

    public String getErgebnis() {
        return ergebnis;
    }

    public void setErgebnis(String ergebnis) {
        this.ergebnis = ergebnis;
    }

    public int getPunkte() {
        return punkte;
    }

    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }

    public int getPlatz() {
        return platz;
    }

    public void setPlatz(int platz) {
        this.platz = platz;
    }

    public String getGeschossen() {
        return geschossen;
    }

    public void setGeschossen(String geschossen) {
        this.geschossen = geschossen;
    }

    public int getKassiert() {
        return kassiert;
    }

    public void setKassiert(int kassiert) {
        this.kassiert = kassiert;
    }

    @Override
    public String toString() {
        return "Spieltage{" +
                "saison='" + saison + '\'' +
                ", spieltag=" + spieltag +
                ", datum=" + datum +
                ", H/A='" + heimOderAuswaerts + '\'' +
                ", gegner='" + gegner + '\'' +
                ", ergebnis='" + ergebnis + '\'' +
                ", punkte=" + punkte +
                ", platz=" + platz +
                ", geschossen='" + geschossen + '\'' +
                ", kassiert=" + kassiert +
                '}';
    }
}
