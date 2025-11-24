package de.kheuwes.footballforwall.model.historie;

import jakarta.persistence.*;

@Entity
@Table(name = "spielerperformance_vw")
public class SpielerSaisonPerformance {
    
    @Id 
    @Column(name = "id_min")
    private Long id;
    String nachname;
    String vorname;
    String saison;  
    String liga;  
    Long spieleSpieler;
    Long spieleTeam;
    Double spieleAnteil;
    Long spielminutenSpieler;
    Long spielminutenTeam;
    Double spielminutenAnteil;
    Long punkteSpieler;
    Long punkteTeam;
    Double punkteAnteil;
    Long spielMin;
    Long spielMax;
    String bemerkungen;  


    public SpielerSaisonPerformance(String nachname, String vorname, String saison, String liga
        , Long spieleSpieler, Long spieleTeam, Double spieleAnteil
        , Long spielminutenSpieler, Long spielminutenTeam, Double spielminutenAnteil
        , Long punkteSpieler, Long punkteTeam, Double punkteAnteil
        , Long spielMin, Long spielMax, String bemerkungen) {
        this.nachname = nachname;
        this.vorname = vorname;
        this.saison = saison;
        this.liga = liga;
        this.spieleSpieler = spieleSpieler;
        this.spieleTeam = spieleTeam;
        this.spieleAnteil = spieleAnteil;
        this.spielminutenSpieler = spielminutenSpieler;
        this.spielminutenTeam = spielminutenTeam;
        this.spielminutenAnteil = spielminutenAnteil;
        this.punkteSpieler = punkteSpieler;
        this.punkteTeam = punkteTeam;
        this.punkteAnteil = punkteAnteil;
        this.spielMin = spielMin;
        this.spielMax = spielMax;
        this.bemerkungen = bemerkungen;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getSaison() {
        return saison;
    }

    public void setSaison(String saison) {
        this.saison = saison;
    }

    public String getLiga() {
        return liga;
    }

    public void setLiga(String liga) {
        this.liga = liga;
    }

    public Long getSpieleSpieler() {
        return spieleSpieler;
    }

    public void setSpieleSpieler(Long spieleSpieler) {
        this.spieleSpieler = spieleSpieler;
    }

    public Long getSpieleTeam() {
        return spieleTeam;
    }

    public void setSpieleTeam(Long spieleTeam) {
        this.spieleTeam = spieleTeam;
    }

    public Double getSpieleAnteil() {
        return spieleAnteil;
    }

    public void setSpieleAnteil(Double spieleAnteil) {
        this.spieleAnteil = spieleAnteil;
    }

    public Long getSpielminutenSpieler() {
        return spielminutenSpieler;
    }

    public void setSpielminutenSpieler(Long spielminutenSpieler) {
        this.spielminutenSpieler = spielminutenSpieler;
    }

    public Long getSpielminutenTeam() {
        return spielminutenTeam;
    }

    public void setSpielminutenTeam(Long spielminutenTeam) {
        this.spielminutenTeam = spielminutenTeam;
    }

    public Double getSpielminutenAnteil() {
        return spielminutenAnteil;
    }

    public void setSpielminutenAnteil(Double spielminutenAnteil) {
        this.spielminutenAnteil = spielminutenAnteil;
    }

    public Long getPunkteSpieler() {
        return punkteSpieler;
    }

    public void setPunkteSpieler(Long punkteSpieler) {
        this.punkteSpieler = punkteSpieler;
    }

    public Long getPunkteTeam() {
        return punkteTeam;
    }

    public void setPunkteTeam(Long punkteTeam) {
        this.punkteTeam = punkteTeam;
    }

    public Double getPunkteAnteil() {
        return punkteAnteil;
    }

    public void setPunkteAnteil(Double punkteAnteil) {
        this.punkteAnteil = punkteAnteil;
    }

    public Long getSpielMin() {
        return spielMin;
    }

    public void setSpielMin(Long spielMin) {
        this.spielMin = spielMin;
    }

    public Long getSpielMax() {
        return spielMax;
    }

    public void setSpielMax(Long spielMax) {
        this.spielMax = spielMax;
    }

    public String getBemerkungen() {
        return bemerkungen;
    }

    public void setBemerkungen(String bemerkungen) {
        this.bemerkungen = bemerkungen;
    }

    @Override
    public String toString() {
        return "SpielerSaisonPerformance [nachname=" + nachname + ", vorname=" + vorname + ", saison=" + saison
                + ", liga=" + liga + ", spieleSpieler=" + spieleSpieler + ", spieleTeam=" + spieleTeam
                + ", spieleAnteil=" + spieleAnteil + ", spielminutenSpieler=" + spielminutenSpieler
                + ", spielminutenTeam=" + spielminutenTeam + ", spielminutenAnteil=" + spielminutenAnteil
                + ", punkteSpieler=" + punkteSpieler + ", punkteTeam=" + punkteTeam + ", punkteAnteil=" + punkteAnteil
                + ", spielMin=" + spielMin + ", spielMax=" + spielMax + ", bemerkungen=" + bemerkungen + "]";
    }


}
