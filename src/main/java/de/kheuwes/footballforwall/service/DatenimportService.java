package de.kheuwes.footballforwall.service;

import de.kheuwes.footballforwall.model.historie.Abschlusstabelleneintrag;
import de.kheuwes.footballforwall.model.historie.Saisoneintrag;
import de.kheuwes.footballforwall.model.historie.Spieltage;
import de.kheuwes.footballforwall.repository.historie.AbschlusstabellenRepository;
import de.kheuwes.footballforwall.repository.historie.SaisoneintragRepository;
import de.kheuwes.footballforwall.repository.historie.SpielerEinsatzRepository;
import de.kheuwes.footballforwall.repository.historie.SpieltageRepository;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Component // Stellt sicher, dass Spring diese Klasse als Bean erkennt und ausführt
public class DatenimportService {
    // ClassPathResource sucht die Dateien im 'src/main/resources'-Verzeichnis des Projekts
    private final AbschlusstabellenRepository repoTabelle;
    private final SaisoneintragRepository repoSaison;
    private final SpielerEinsatzRepository repoSpielerEinsatz;
    private final SpieltageRepository repoSpieltage;

    private static final String JDBC_URL = "jdbc:h2:file:./data/fussballDB";
    private static final String USER = "sa";
    private static final String PASSWORD = "password";
    private static final String INSERT_SQL = "INSERT INTO spielereinsaetze (ID, SAISON, SPIEL, NACHNAME, VORNAME, EINSATZ, GRUPPE) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final int BATCH_SIZE = 1000;
    
    // Konstruktor-Injektion (bevorzugte Methode in Spring)
    public DatenimportService(AbschlusstabellenRepository repoTabelle, SaisoneintragRepository repoSaison,
    SpielerEinsatzRepository repoSpielerEinsatz, SpieltageRepository repoSpieltage) {
        this.repoTabelle = repoTabelle;
        this.repoSaison = repoSaison;
        this.repoSpielerEinsatz = repoSpielerEinsatz;
        this.repoSpieltage = repoSpieltage;
    }

    public int importAllData(){
        int anz=0;
        try {
            anz+=importCsv("historie/abschlusstabellen.csv",  12, repoTabelle, this::createTabelleEntry );
            anz+=importCsv("historie/saisons.csv",  10, repoSaison, this::createSaisonEntry );
            anz+=importKader("historie/kader.csv",  6);
            anz+=importCsv("historie/spieltage.csv",  10, repoSpieltage, this::createSpieltageEntry );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return anz;
    }

    @Transactional
    public <T> int importCsv(String csvFileName, int minColumnCount, JpaRepository<T, ?> repository, Function<String[], T> entryCreator
    ) throws Exception {

        System.out.println("Starte Datenimport aus: " + csvFileName);

        try (BufferedReader reader = getReaderForCsv(csvFileName)) {
            if(csvFileName.equals("historie/kader.csv")){ 
                repoSpielerEinsatz.bulkDeleteAll();
            }else{
                repository.deleteAll();
            }

            String line;
            boolean isHeader = true;
            int importCounter = 0;

            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] values = line.split(";", -1);

                if (values.length < minColumnCount) {
                    System.err.println("WARNUNG: Zeile übersprungen aufgrund fehlender Spalten: " + line);
                    continue;
                }

                T eintrag = entryCreator.apply(values);
                repository.save(eintrag);
                importCounter++;
            }

            System.out.println("Datenimport erfolgreich abgeschlossen. " + importCounter + " Einträge importiert.");
            return importCounter;

        } catch (Exception e) {
            System.err.println("FEHLER beim CSV-Datenimport: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private static List<String> readCsv(String filePath) throws Exception {
        System.out.println("Lese Semikolon-getrennte CSV-Datei: " + filePath);
        List<String> lines = new ArrayList<>();
        

        try (InputStream inputStream = DatenimportService.class.getClassLoader().getResourceAsStream(filePath);
        InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1);
                 BufferedReader br = new BufferedReader(isr)) {
                    String line = br.readLine();
                    while ((line = br.readLine()) != null) {
                    if (!line.trim().isEmpty()) { 
                        lines.add(line);
                    }
                }
            return lines;
        }
    }

    /**
     * Speichert die gelesenen CSV-Records in die H2-Datenbank.
     */
    private static void importDataToH2(List<String> records) throws Exception {
        Class.forName("org.h2.Driver"); 

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

            connection.setAutoCommit(false); 
            int count = 0;

            System.out.println("Starte Import von " + records.size() + " Datensätzen...");

            for (String record : records) {
                //System.out.println(" * record " + record);
                // Zugriff auf die Spalten über den Index (0 bis 5)
                String[] values = record.split(";", -1);
                String saison = values[0].trim();
                String spielStr = values[1].trim();
                String spielValueClean = spielStr
                    // 1. Entferne alle Zeichen, die KEINE Ziffern sind (0-9)
                    .replaceAll("[^0-9]", "")
                    // 2. Trimme resultierende Leerzeichen (obwohl nach dem Regex unnötig, schadet es nicht)
                        .trim();
                
                // Konvertierung der Zahlen (Spiel)
                int spiel = Integer.parseInt(spielValueClean); 
                
                String nachname = values[2].trim();
                String vorname = values[3].trim();
                String einsatz = values[4].trim();
                String gruppe = values[5].trim();

                // Parameter für das Prepared Statement setzen (Index beginnt bei 1)
                
                preparedStatement.setInt(1, count);
                preparedStatement.setString(2, saison);
                preparedStatement.setInt(3, spiel);
                preparedStatement.setString(4, nachname);
                preparedStatement.setString(5, vorname);
                preparedStatement.setString(6, einsatz);
                preparedStatement.setString(7, gruppe);
                
                preparedStatement.addBatch();
                count++;

                // Batch ausführen
                if (count % BATCH_SIZE == 0) {
                    preparedStatement.executeBatch();
                    connection.commit(); 
                    System.out.println("-> Batch # " + (count / BATCH_SIZE) + " committed.");
                }
            }
            
            // Verbleibenden Batch ausführen
            preparedStatement.executeBatch(); 
            connection.commit(); 
            connection.setAutoCommit(true); 
            
            System.out.println("✅ Import erfolgreich abgeschlossen. " + records.size() + " Datensätze importiert.");

        } 
    }

    @Transactional
    public int importKader(String csvFileName, int minColumnCount) throws Exception {

        System.out.println("Starte Datenimport aus: " + csvFileName);
        List<String> records = List.of();

        try {
            records = readCsv(csvFileName);
            if (!records.isEmpty()) {
                importDataToH2(records);
            }
        } catch (Exception e) {
            System.err.println("Ein Fehler ist aufgetreten: " + e.getMessage());
            e.printStackTrace();
        }
        return records.size();
    }

    // Helferfunktion für das Parsing von Integer-Werten
    // Wir verwenden Integer.valueOf() und kümmern uns um mögliche Leerstrings oder Fehler.
    java.util.function.Function<String, Integer> safeParseInt = s -> {
        if (s == null || s.trim().isEmpty()) return 0; // Setze 0, wenn leer
        try {
            return Integer.valueOf(s.trim());
        } catch (NumberFormatException e) {
            return 0; // Rückfall auf 0 bei einem Fehler
        }
    };
    

    /**
     * Helfermethode zum Erstellen eines Spieltageeintrags aus den String-Werten.
     */
    private Spieltage createSpieltageEntry(String[] values) {
        // Die Spalten sind: Saison(0);Spieltag(1);Datum(2);H/A(3);Gegner(4);Ergebnis(5);Punkte(6);Platz(7);Geschossen(8);Kassiert(9)
        Spieltage spieltag = new Spieltage();
        spieltag.setSaison(values[0].trim());
        spieltag.setSpieltag(safeParseInt.apply(values[1]));
        spieltag.setDatum(values[2].trim());
        spieltag.setHeimOderAuswaerts(values[3].trim());
        spieltag.setGegner(values[4].trim());
        spieltag.setErgebnis(values[5].trim());
        spieltag.setPunkte(safeParseInt.apply(values[6]));
        spieltag.setPlatz(safeParseInt.apply(values[7]));
        spieltag.setGeschossen(values[8].trim());
        spieltag.setKassiert(values[9]);
        return spieltag;
    }


    /**
     * Helfermethode zum Erstellen eines Abschlusstabelleneintrags aus den String-Werten.
     */
    private Abschlusstabelleneintrag createTabelleEntry(String[] values) {        
        // Die Spalten sind: Saison(0);Quelle(1);Liga(2);Platz(3);Mannschaft(4);Spiele(5);g(6);u(7);v(8);Tore(9);Diff(10);Punkte(11)
        return new Abschlusstabelleneintrag(
                values[0].trim(), values[1].trim(),  values[2].trim(), 
                safeParseInt.apply(values[3]),
                values[4].trim(), 
                safeParseInt.apply(values[5]), safeParseInt.apply(values[6]), safeParseInt.apply(values[7]), safeParseInt.apply(values[8]),                    // v (Integer)
                values[9].trim(), 
                safeParseInt.apply(values[10]), safeParseInt.apply(values[11])
        );
    }

    /**
     * Helfermethode zum Erstellen eines Abschlusstabelleneintrags aus den String-Werten.
     */
    private Saisoneintrag createSaisonEntry(String[] values) {        
        // Die Spalten sind: Saison;Liga;Spiele;Platz;Punkte;Bemerkungen;Performanceindex;Import_Tabelle;Import_Spiele;Quelle        
        return new Saisoneintrag(
                values[0].trim(), values[1].trim(),
                safeParseInt.apply(values[2]), safeParseInt.apply(values[3]), safeParseInt.apply(values[4]),
                values[5].trim(),
                safeParseInt.apply(values[6]),
                values[7].trim(), values[8].trim(), values[9].trim()
        );
    }

    //KADER        : Saison;Nachname;Vorname;1;2;3;4;5;6;7;8;9;10;11;12;13;14;15;16;17;18;19;20;21;22;23;24;25;26;27;28;29;30;31;32;33;34
    //SPIELTAGE    : Saison;Spieltag;Datum;H/A;Gegner;Ergebnis;Punkte;Platz;Geschossen;Kassiert
    //EINSATZZEITEN: Saison;Nachname;Vorname;LfdNr;Gesamt;AnzSpiele;Tore;1;2;3;4;5;6;7;8;9;10;11;12;13;14;15;16;17;18;19;20;21;22;23;24;25;26;27;28;29;30;31;32;33;34
    
    private BufferedReader getReaderForCsv(String csvFile) throws Exception {
        return new BufferedReader(
                new InputStreamReader( new ClassPathResource(csvFile).getInputStream(),
                        StandardCharsets.UTF_16LE));
    }
}