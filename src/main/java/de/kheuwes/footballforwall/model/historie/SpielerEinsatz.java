package de.kheuwes.footballforwall.model.historie;

import jakarta.persistence.*;

@Entity
@Table(name = "spielereinsaetze_vw")
public class SpielerEinsatz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String saison;
    private Integer spiel;
    private String nachname;
    private String vorname;

    private String einsatz;
    private String gruppe; 
    private Integer punkte;   
    private Integer spielminuten;  
    private String datum;
    private String ha; 
    private String ergebnis;
    private String gegner; 

    public SpielerEinsatz(Long id, String saison, Integer spiel, String nachname, String vorname, String einsatz,
            String gruppe, Integer punkte, Integer spielminuten, String datum, String ha, String ergebnis, String gegner) {
        this.id = id;
        this.saison = saison;
        this.spiel = spiel;
        this.nachname = nachname;
        this.vorname = vorname;
        this.einsatz = einsatz;
        this.gruppe = gruppe;
        this.punkte = punkte;
        this.spielminuten = spielminuten;
        this.datum = datum;
        this.ha = ha;
        this.ergebnis = ergebnis;
        this.gegner = gegner;   
    }
    public SpielerEinsatz() {
    }
    // --- Getter & Setter ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSaison() { return saison; }
    public void setSaison(String saison) { this.saison = saison; }

    public String getNachname() { return nachname; }
    public void setNachname(String nachname) { this.nachname = nachname; }

    public String getVorname() { return vorname; }
    public void setVorname(String vorname) { this.vorname = vorname; }

    public Integer getSpiel() {       return spiel;   }
    public void setSpiel(Integer spiel) {     this.spiel = spiel;  }
    public String getEinsatz() {      return einsatz;   }
    public void setEinsatz(String einsatz) {      this.einsatz = einsatz;   }
    public String getGruppe() {     return gruppe;  }
    public void setGruppe(String gruppe) {    this.gruppe = gruppe;   }
    
    public Integer getPunkte() {return punkte;}
    public void setPunkte(Integer punkte) {this.punkte = punkte;}
    public Integer getSpielminuten() {return spielminuten;}
    public void setSpielminuten(Integer spielminuten) {this.spielminuten = spielminuten;}
    public String getDatum() {return datum;}    
    public void setDatum(String datum) {this.datum = datum;}
    public String getHa() {return ha;}
    public void setHa(String ha) {this.ha = ha;}
    public String getErgebnis() {return ergebnis;}
    public void setErgebnis(String ergebnis) {this.ergebnis = ergebnis;}
    public String getGegner() {return gegner;}
    public void setGegner(String gegner) {this.gegner = gegner;}    
    
    @Override
    public String toString() {
        return "SpielerEinsatz [id=" + id + ", saison=" + saison + ", spiel=" + spiel + ", nachname=" + nachname
                + ", vorname=" + vorname + ", einsatz=" + einsatz + ", gruppe=" + gruppe + ", punkte=" + punkte + "]";
    }
    

}
