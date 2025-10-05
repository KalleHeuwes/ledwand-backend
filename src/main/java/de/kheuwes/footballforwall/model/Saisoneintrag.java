package de.kheuwes.footballforwall.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

/**
 * Entität zur Speicherung von Saisondaten und Performance-Informationen.
 */
@Entity
public class Saisoneintrag {

    /**
     * Eindeutiger Identifikator für die Entität.
     * In diesem Fall wird der Saison-String als ID verwendet,
     * da er wahrscheinlich eindeutig ist. Alternativ könnte eine Long-ID
     * mit @GeneratedValue verwendet werden.
     */
    @Id
    @Column(name = "saison")
    private String saison; // 2025/26

    @Column(name = "liga")
    private String liga; // Landesliga 4

    @Column(name = "spiele")
    private int spiele; // 8

    @Column(name = "platz")
    private int platz; // 6

    @Column(name = "punkte")
    private int punkte; // 14

    @Column(name = "bemerkungen")
    private String bemerkungen; // Aufsteiger (kann leer sein)

    @Column(name = "performance_index")
    private int performanceIndex; // 419

    @Column(name = "import_tabelle")
    private String importTabelle; // 2526_Abschluss

    @Column(name = "import_spiele")
    private String importSpiele; // (kann leer sein)

    @Column(name = "quelle")
    private String quelle; // FuPa.Net

    // Standard-Konstruktor (für JPA erforderlich)
    public Saisoneintrag() {
    }

    /**
     * Konstruktor zur Initialisierung aller Felder.
     */
    public Saisoneintrag(String saison, String liga, int spiele, int platz, int punkte, String bemerkungen, int performanceIndex, String importTabelle, String importSpiele, String quelle) {
        this.saison = saison;
        this.liga = liga;
        this.spiele = spiele;
        this.platz = platz;
        this.punkte = punkte;
        this.bemerkungen = bemerkungen;
        this.performanceIndex = performanceIndex;
        this.importTabelle = importTabelle;
        this.importSpiele = importSpiele;
        this.quelle = quelle;
    }

    //region Getter und Setter
    // Die Getter und Setter sind für den Zugriff auf die Felder erforderlich.

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

    public int getSpiele() {
        return spiele;
    }

    public void setSpiele(int spiele) {
        this.spiele = spiele;
    }

    public int getPlatz() {
        return platz;
    }

    public void setPlatz(int platz) {
        this.platz = platz;
    }

    public int getPunkte() {
        return punkte;
    }

    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }

    public String getBemerkungen() {
        return bemerkungen;
    }

    public void setBemerkungen(String bemerkungen) {
        this.bemerkungen = bemerkungen;
    }

    public int getPerformanceIndex() {
        return performanceIndex;
    }

    public void setPerformanceIndex(int performanceIndex) {
        this.performanceIndex = performanceIndex;
    }

    public String getImportTabelle() {
        return importTabelle;
    }

    public void setImportTabelle(String importTabelle) {
        this.importTabelle = importTabelle;
    }

    public String getImportSpiele() {
        return importSpiele;
    }

    public void setImportSpiele(String importSpiele) {
        this.importSpiele = importSpiele;
    }

    public String getQuelle() {
        return quelle;
    }

    public void setQuelle(String quelle) {
        this.quelle = quelle;
    }
    //endregion

    @Override
    public String toString() {
        return "SaisonPerformance{" +
               "saison='" + saison + '\'' +
               ", liga='" + liga + '\'' +
               ", spiele=" + spiele +
               ", platz=" + platz +
               ", punkte=" + punkte +
               ", bemerkungen='" + bemerkungen + '\'' +
               ", performanceIndex=" + performanceIndex +
               ", importTabelle='" + importTabelle + '\'' +
               ", importSpiele='" + importSpiele + '\'' +
               ", quelle='" + quelle + '\'' +
               '}';
    }
}
