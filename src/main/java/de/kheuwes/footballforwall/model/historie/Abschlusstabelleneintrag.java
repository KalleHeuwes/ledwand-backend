package de.kheuwes.footballforwall.model.historie;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "abschlusstabellen")
public class Abschlusstabelleneintrag {

    // Technischer Primärschlüssel für die Datenbank
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Datenfelder entsprechend der CSV-Struktur (Saison;Quelle;Liga;Platz;Mannschaft;Spiele;g;u;v;Tore;Diff;Punkte)
    private String saison;
    private String quelle;
    private String liga;
    private Integer platz;
    private String mannschaft;
    private Integer spiele;
    private Integer g; // Gewonnen
    private Integer u; // Unentschieden
    private Integer v; // Verloren
    private String tore; // Wird als String gespeichert (z.B. "94:30")
    @Column(name = "tore_diff")
    private Integer diff; // Tordifferenz
    private Integer punkte;

    // --- Konstruktoren ---

    // Standard-Konstruktor (für JPA benötigt)
    public Abschlusstabelleneintrag() {
    }

    // Konstruktor für den einfachen Import der Daten (ohne ID)
    public Abschlusstabelleneintrag(String saison, String quelle, String liga, Integer platz, String mannschaft, Integer spiele, Integer g, Integer u, Integer v, String tore, Integer diff, Integer punkte) {
        this.saison = saison;
        this.quelle = quelle;
        this.liga = liga;
        this.platz = platz;
        this.mannschaft = mannschaft;
        this.spiele = spiele;
        this.g = g;
        this.u = u;
        this.v = v;
        this.tore = tore;
        this.diff = diff;
        this.punkte = punkte;
    }


    // --- Getter und Setter (für Spring Boot/JPA benötigt) ---
    // (Auszug, alle Felder benötigen diese Methoden)

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

    public String getMannschaft() {
        return mannschaft;
    }

    public void setMannschaft(String mannschaft) {
        this.mannschaft = mannschaft;
    }
    
    // ... Fügen Sie Getter und Setter für alle anderen Felder (quelle, liga, platz, spiele, g, u, v, tore, diff, punkte) hinzu
    
    public String getQuelle() {
        return quelle;
    }

    public void setQuelle(String quelle) {
        this.quelle = quelle;
    }

    public String getLiga() {
        return liga;
    }

    public void setLiga(String liga) {
        this.liga = liga;
    }

    public Integer getPlatz() {
        return platz;
    }

    public void setPlatz(Integer platz) {
        this.platz = platz;
    }

    public Integer getSpiele() {
        return spiele;
    }

    public void setSpiele(Integer spiele) {
        this.spiele = spiele;
    }

    public Integer getG() {
        return g;
    }

    public void setG(Integer g) {
        this.g = g;
    }

    public Integer getU() {
        return u;
    }

    public void setU(Integer u) {
        this.u = u;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getTore() {
        return tore;
    }

    public void setTore(String tore) {
        this.tore = tore;
    }

    public Integer getDiff() {
        return diff;
    }

    public void setDiff(Integer diff) {
        this.diff = diff;
    }

    public Integer getPunkte() {
        return punkte;
    }

    public void setPunkte(Integer punkte) {
        this.punkte = punkte;
    }
}