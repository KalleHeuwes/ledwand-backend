package de.kheuwes.footballforwall.service;

import de.kheuwes.footballforwall.model.Abschlusstabelleneintrag;
import de.kheuwes.footballforwall.model.Saisoneintrag;
import de.kheuwes.footballforwall.repository.AbschlusstabellenRepository;
import de.kheuwes.footballforwall.repository.SaisoneintragRepository;

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
    private final SaisoneintragRepository repoSaison;
    
    // Konstruktor-Injektion (bevorzugte Methode in Spring)
    public DatenimportService(AbschlusstabellenRepository repository, SaisoneintragRepository repoSaison) {
        this.repository = repository;
        this.repoSaison = repoSaison;
    }

    private BufferedReader getReaderForCsv(String csvFile) throws Exception {
        return new BufferedReader(
                new InputStreamReader( new ClassPathResource(csvFile).getInputStream(),
                        StandardCharsets.UTF_8));
    }

    public int importAllData(){
        int anz=0;
        try {
            anz+=importAbschlusstabellen();
            anz+=importSaisons();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return anz;
    }

    @Transactional // Stellt sicher, dass der gesamte Import atomar ist
    public int importAbschlusstabellen() throws Exception {
        final String CSV_FILE = "abschlusstabellen.csv";    // Der Dateiname Ihrer hochgeladenen Datei
        System.out.println("Starte Datenimport aus: " + CSV_FILE);

        // ClassPathResource sucht die Datei im 'src/main/resources'-Verzeichnis des Projekts
        try (BufferedReader reader = getReaderForCsv(CSV_FILE)) {
            repository.deleteAll(); // Löscht alle vorhandenen Einträge, um Duplikate zu vermeiden

            String line;
            boolean isHeader = true;
            int importCounter = 0;

            while ((line = reader.readLine()) != null) {        // Zeile für Zeile die Datei lesen
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

                Abschlusstabelleneintrag eintrag = createEntry(values);
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

    @Transactional // Stellt sicher, dass der gesamte Import atomar ist
    public int importSaisons() throws Exception {
        final String CSV_FILE = "saisons.csv";    // Der Dateiname Ihrer hochgeladenen Datei
        System.out.println("Starte Datenimport aus: " + CSV_FILE);

        // ClassPathResource sucht die Datei im 'src/main/resources'-Verzeichnis des Projekts
        try (BufferedReader reader = getReaderForCsv(CSV_FILE)) {
            repoSaison.deleteAll(); // Löscht alle vorhandenen Einträge, um Duplikate zu vermeiden

            String line;
            boolean isHeader = true;
            int importCounter = 0;

            while ((line = reader.readLine()) != null) {        // Zeile für Zeile die Datei lesen
                if (isHeader) {
                    isHeader = false; // Überspringe die Kopfzeile
                    continue;
                }

                // Trenne die Spalten basierend auf dem Semikolon (;)
                // Verwende die korrekte Trennungsmethode, die leere Strings vermeidet, 
                // falls Daten fehlen sollten.
                String[] values = line.split(";", -1); 

                // Stelle sicher, dass wir genügend Spalten haben (mindestens 10)
                if (values.length < 10) {
                    System.err.println("WARNUNG: Zeile übersprungen aufgrund fehlender Spalten: " + line);
                    continue;
                }

                Saisoneintrag eintrag = createSaisonEntry(values);
                repoSaison.save(eintrag);
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
    /**
     * Helfermethode zum Erstellen eines Abschlusstabelleneintrags aus den String-Werten.
     */
    private Saisoneintrag createSaisonEntry(String[] values) {
        
        // Die Spalten sind: Saison;Liga;Spiele;Platz;Punkte;Bemerkungen;Performanceindex;Import_Tabelle;Import_Spiele;Quelle
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
// Die Spalten sind: Saison;Liga;Spiele;Platz;Punkte;Bemerkungen;Performanceindex;
//Import_Tabelle;Import_Spiele;Quelle
        
        return new Saisoneintrag(
                values[0].trim(), 
                values[1].trim(),
                safeParseInt.apply(values[2]),
                safeParseInt.apply(values[3]),
                safeParseInt.apply(values[4]),
                values[5].trim(),
                safeParseInt.apply(values[6]),
                values[7].trim(),
                values[8].trim(),
                values[9].trim()
        );
    }
}