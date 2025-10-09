package de.kheuwes.footballforwall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import de.kheuwes.footballforwall.model.historie.Abschlusstabelleneintrag;
import de.kheuwes.footballforwall.model.historie.Saisoneintrag;
import de.kheuwes.footballforwall.model.historie.Spieltage;
import de.kheuwes.footballforwall.repository.historie.AbschlusstabellenRepository;
import de.kheuwes.footballforwall.repository.historie.SaisoneintragRepository;
import de.kheuwes.footballforwall.repository.historie.SpieltageRepository;
import de.kheuwes.footballforwall.service.DatenimportService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class HistorieController {
    @Autowired
    private AbschlusstabellenRepository repoAbschlusstabellen; 

    @Autowired
    private SpieltageRepository repoSpieltage; 

    @Autowired
    private SaisoneintragRepository repoSaisons;

    private final DatenimportService datenimportService;

    // Injektion des neuen Service
    public HistorieController(DatenimportService datenimportService) {
        this.datenimportService = datenimportService;
    }

    @GetMapping("/historie/abschlusstabelle/{saison}")
    public List<Abschlusstabelleneintrag> getBySaison(@PathVariable String saison) {
        String saisonCalc = saison.substring(0, 4) + "/" + saison.substring(4, 6);
        return repoAbschlusstabellen.findBySaison(saisonCalc); // Findet alle Einträge für die Saison
    }

    @GetMapping("/historie/spieltage/{saison}")
    public List<Spieltage> getSpieltageBySaison(@PathVariable String saison) {
        String saisonCalc = saison.substring(0, 4) + "/" + saison.substring(4, 6);
        return repoSpieltage.findBySaison(saisonCalc); // Findet alle Einträge für die Saison
    }

    @GetMapping("/historie/saisons")
    public List<Saisoneintrag> getSaisons() {
        return repoSaisons.findAll();
    }

    @GetMapping("/historie")
    public String getHistorie() {
        return "Dummy"; 
    }


    // Endpunkt zum expliziten Start des Datenimports
    @PostMapping("/historie/import")
    public ResponseEntity<String> startCsvImport() {
        try {
            int count = datenimportService.importAllData();
            
            if (count > 0) {
                // Erfolgreiche Antwort (200 OK)
                return ResponseEntity.ok("Datenimport erfolgreich. " + count + " Einträge zur Datenbank hinzugefügt.");
            } else {
                // Erfolgreiche Antwort, aber mit Hinweis, dass keine neuen Daten geladen wurden
                return ResponseEntity.ok("Datenbank war bereits gefüllt. Import übersprungen.");
            }
            
        } catch (Exception e) {
            // Internen Serverfehler (500) bei Fehlschlag zurückgeben
            return ResponseEntity.internalServerError().body("FEHLER beim Datenimport: " + e.getMessage());
        }
    }
}
