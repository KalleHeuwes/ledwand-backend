package de.kheuwes.footballforwall.model.historie;

import jakarta.persistence.*;

@Entity
@Table(name = "spielereinsaetze")
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

    

    public SpielerEinsatz(Long id, String saison, Integer spiel, String nachname, String vorname, String einsatz,
            String gruppe) {
        this.id = id;
        this.saison = saison;
        this.spiel = spiel;
        this.nachname = nachname;
        this.vorname = vorname;
        this.einsatz = einsatz;
        this.gruppe = gruppe;
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
    @Override
    public String toString() {
        return "SpielerEinsatz [id=" + id + ", saison=" + saison + ", spiel=" + spiel + ", nachname=" + nachname
                + ", vorname=" + vorname + ", einsatz=" + einsatz + ", gruppe=" + gruppe + "]";
    }
    

}
