package de.kheuwes.footballforwall.model.historie;

import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "spielereinsaetze")
public class SpielerEinsatz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String saison;
    private String nachname;
    private String vorname;

    // 🔹 Die Map mit JPA-kompatibler Definition
    @ElementCollection
    @CollectionTable(
        name = "spieler_einsatz_spiele",
        joinColumns = @JoinColumn(name = "spieler_einsatz_id")
    )
    @MapKeyColumn(name = "spielnummer")
    @Column(name = "wert")
    private Map<Integer, String> spiele = new HashMap<>();

    // --- Getter & Setter ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSaison() { return saison; }
    public void setSaison(String saison) { this.saison = saison; }

    public String getNachname() { return nachname; }
    public void setNachname(String nachname) { this.nachname = nachname; }

    public String getVorname() { return vorname; }
    public void setVorname(String vorname) { this.vorname = vorname; }

    public Map<Integer, String> getSpiele() { return spiele; }
    public void setSpiele(Map<Integer, String> spiele) { this.spiele = spiele; }

    public String getSpiel(int nummer) {
        return spiele.getOrDefault(nummer, "");
    }

    public void setSpiel(int nummer, String wert) {
        spiele.put(nummer, wert);
    }

    @Override
    public String toString() {
        return saison + " - " + nachname + ", " + vorname + " (" + spiele.size() + " Spiele)";
    }
}
