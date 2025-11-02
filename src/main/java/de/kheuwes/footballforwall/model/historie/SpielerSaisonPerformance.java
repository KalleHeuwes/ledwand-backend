package de.kheuwes.footballforwall.model.historie;

public class SpielerSaisonPerformance {
    String saison;  
    String nachname;
    String vorname;
    Long anzSpiele;
    Long anzMinuten;
    Long anzTore;

    public SpielerSaisonPerformance(String saison, String nachname, String vorname
    , Long anzSpiele, Long anzMinuten, Long anzTore) {
        this.saison = saison;
        this.nachname = nachname;
        this.vorname = vorname;
        this.anzSpiele = anzSpiele;
        this.anzMinuten = anzMinuten;
        this.anzTore = anzTore;
    }
    
    // Getter und Setter
    public String getSaison() {
        return saison;
    }

    public void setSaison(String saison) {
        this.saison = saison;
    }

    public Long getAnzSpiele() {
        return anzSpiele;
    }

    public void setAnzSpiele(Long anzSpiele) {
        this.anzSpiele = anzSpiele;
    }

    public Long getAnzMinuten() {
        return anzMinuten;
    }

    public void setAnzMinuten(Long anzMinuten) {
        this.anzMinuten = anzMinuten;
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
    
    public Long getAnzTore() {
        return anzTore;
    }

    public void setAnzTore(Long anzTore) {
        this.anzTore = anzTore;
    }
}
