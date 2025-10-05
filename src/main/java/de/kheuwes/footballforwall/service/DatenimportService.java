package de.kheuwes.footballforwall.service;

import de.kheuwes.footballforwall.model.Abschlusstabelleneintrag;
import de.kheuwes.footballforwall.repository.AbschlusstabellenRepository;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component // Stellt sicher, dass Spring diese Klasse als Bean erkennt und ausführt
public class DatenimportService {

    // Wir injizieren das Repository, um Daten in die Datenbank zu speichern
    private final AbschlusstabellenRepository repository;

    // Konstruktor-Injektion (bevorzugte Methode in Spring)
    public DatenimportService(AbschlusstabellenRepository repository) {
        this.repository = repository;
    }

    @Transactional // Stellt sicher, dass der gesamte Import atomar ist
    public int importData() throws Exception {
        // Der Dateiname Ihrer hochgeladenen Datei
        final String CSV_FILE = "abschlusstabellen.csv";

        System.out.println("Starte Datenimport aus: " + CSV_FILE);

        // ClassPathResource sucht die Datei im 'src/main/resources'-Verzeichnis des Projekts
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new ClassPathResource(CSV_FILE).getInputStream(),
                        StandardCharsets.UTF_8))) {

            String line;
            boolean isHeader = true;
            int importCounter = 0;

            // Zeile für Zeile die Datei lesen
            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false; // Überspringe die Kopfzeile
                    continue;
                }

                // Trenne die Spalten basierend auf dem Semikolon (;)
                // Verwende die korrekte Trennungsmethode, die leere Strings vermeidet, 
                // falls Daten fehlen sollten.
                String[] values = line.split(";", -1); 

                // Stelle sicher, dass wir genügend Spalten haben (mindestens 12)
                if (values.length < 12) {
                    System.err.println("WARNUNG: Zeile übersprungen aufgrund fehlender Spalten: " + line);
                    continue;
                }

                // Erzeuge das Entity-Objekt
                Abschlusstabelleneintrag eintrag = createEntry(values);
                
                // Speichere den Eintrag in der H2-Datenbank
                repository.save(eintrag);
                importCounter++;
            }

            System.out.println("Datenimport erfolgreich abgeschlossen. " + importCounter + " Einträge importiert.");
            return importCounter;

        } catch (Exception e) {
            System.err.println("FEHLER beim CSV-Datenimport: " + e.getMessage());
            e.printStackTrace();
            throw e; // Anwendung beenden, wenn Import fehlschlägt
        }
    }

    /**
     * Helfermethode zum Erstellen eines Abschlusstabelleneintrags aus den String-Werten.
     */
    private Abschlusstabelleneintrag createEntry(String[] values) {
        
        // Die Spalten sind: Saison(0);Quelle(1);Liga(2);Platz(3);Mannschaft(4);Spiele(5);g(6);u(7);v(8);Tore(9);Diff(10);Punkte(11)
        
        // Wir verwenden Integer.valueOf() und kümmern uns um mögliche Leerstrings oder Fehler.
        // Bei Importdaten ist es oft sicherer, wenn eine Nummer fehlt, den Wert auf null oder 0 zu setzen.
        
        // Helferfunktion für das Parsing von Integer-Werten
        java.util.function.Function<String, Integer> safeParseInt = s -> {
            if (s == null || s.trim().isEmpty()) return 0; // Setze 0, wenn leer
            try {
                return Integer.valueOf(s.trim());
            } catch (NumberFormatException e) {
                // Hier könnten Sie zusätzliche Logging-Logik einfügen
                return 0; // Rückfall auf 0 bei einem Fehler
            }
        };

        return new Abschlusstabelleneintrag(
                values[0].trim(),                                 // saison (String)
                values[1].trim(),                                 // quelle (String)
                values[2].trim(),                                 // liga (String)
                safeParseInt.apply(values[3]),                    // platz (Integer)
                values[4].trim(),                                 // mannschaft (String)
                safeParseInt.apply(values[5]),                    // spiele (Integer)
                safeParseInt.apply(values[6]),                    // g (Integer)
                safeParseInt.apply(values[7]),                    // u (Integer)
                safeParseInt.apply(values[8]),                    // v (Integer)
                values[9].trim(),                                 // tore (String)
                safeParseInt.apply(values[10]),                   // diff (Integer)
                safeParseInt.apply(values[11])                    // punkte (Integer)
        );
    }
}