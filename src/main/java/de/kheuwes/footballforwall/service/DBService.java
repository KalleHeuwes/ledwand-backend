package de.kheuwes.footballforwall.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/*
    Alles rund um die Datenbank

*/
@Service
public class DBService implements CommandLineRunner {

    // Spring Boot injiziert den konfigurierten JdbcTemplate.
    private final JdbcTemplate jdbcTemplate;

    // Konstruktor-Injektion (empfohlen)
    public DBService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Die Methode, die beim Start der Anwendung automatisch ausgeführt wird
    @Override
    public void run(String... args) throws Exception {
        // Rufen Sie Ihre eigentliche Datenbankerstellungs-Logik auf
        createDatabase();
    }
    @Value("${spring.datasource.url}")
    private String JDBC_URL;
    
    @Value("${spring.datasource.username}")
    private String USER;

    @Value("${spring.datasource.password}")
    private String PASSWORD;

    public void createDatabase(){
        Connection conn = null;
        Statement stmt = null;

        try  {
                
            Class.forName("org.h2.Driver"); 

            System.out.println("Verbindung zur Datenbank " + JDBC_URL + " herstellen...");
            conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Verbindung erfolgreich hergestellt.");

            execDDL(conn, "Abschlusstabelle", getDDLAbschlusstabelle());
            execDDL(conn, "Saisons", getDDLSaisons());
            execDDL(conn, "Kader", getDDLKader());
            //execDDL(conn, "SpielerEinsatz", getDDLSpielerEinsatz());

        } catch (SQLException se) {
            // Fehler bei der JDBC-Verarbeitung
            se.printStackTrace();
        } catch (Exception e) {
            // Fehler bei Class.forName
            e.printStackTrace();
        } finally {
            // Schritt 5: Ressourcen schließen
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
                // Kann ignoriert werden
            }
            try {
                if (conn != null) conn.close();
                System.out.println("Verbindung geschlossen.");
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    private static void execDDL(Connection conn, String objName, String sql) {
        Statement stmt;
        try {
            stmt = conn.createStatement();
            System.out.println("Erzeuge " + objName + "...");
            stmt.executeUpdate(sql);
            System.out.println("Tabelle " + objName + " erfolgreich erstellt.");
            stmt.close(); 
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getDDLAbschlusstabelle(){
        //Saison;Quelle;Liga;Platz;Mannschaft;Spiele;g;u;v;Tore;Diff;Punkte
        return "CREATE TABLE IF NOT EXISTS abschlusstabelle (\n" +
               "    id SERIAL PRIMARY KEY,\n" +
               "    saison VARCHAR(10) NOT NULL,\n" +
               "    quelle VARCHAR(100) NOT NULL,\n" +
               "    liga VARCHAR(100) NOT NULL,\n" +
               "    platz INT NOT NULL,\n" +
               "    mannschaft VARCHAR(100) NOT NULL,\n" +
               "    spiele INT NOT NULL,\n" +
               "    g INT NOT NULL,\n" +
               "    u INT NOT NULL,\n" +
               "    v INT NOT NULL,\n" +
               "    tore VARCHAR(10) NOT NULL,\n" +
               "    tore_diff INT NOT NULL,\n" +
               "    punkte INT NOT NULL\n" +
               ");";
    }

    public static String getDDLSaisons(){
        //Saison;Liga;Spiele;Platz;Punkte;Bemerkungen;Performanceindex;Import_Tabelle;Import_Spiele;Quelle
        return "CREATE TABLE IF NOT EXISTS saisons (\n" +
               "    id SERIAL PRIMARY KEY,\n" +
               "    saison VARCHAR(10) NOT NULL,\n" +
               "    liga VARCHAR(100) NOT NULL,\n" +
               "    spiele INT NOT NULL,\n" +
               "    platz INT NOT NULL,\n" +
               "    punkte INT NOT NULL,\n" +
               "    bemerkungen TEXT,\n" +
               "    performanceindex DOUBLE PRECISION,\n" +
               "    import_tabelle VARCHAR(100),\n" +
               "    import_spiele INT,\n" +
               "    quelle VARCHAR(100) NOT NULL\n" +
               ");";
    }

    public static String getDDLKader(){
        //Saison;Spiel;Nachname;Vorname;Einsatz;Gruppe
        return "CREATE TABLE IF NOT EXISTS kader (\n" +
               "    id SERIAL PRIMARY KEY,\n" +
               "    saison VARCHAR(10) NOT NULL,\n" +
               "    spiel INT NOT NULL,\n" +
               "    nachname VARCHAR(100) NOT NULL,\n" +
               "    vorname VARCHAR(100) NOT NULL,\n" +
               "    einsatz VARCHAR(50) NOT NULL,\n" +
               "    gruppe VARCHAR(50) NOT NULL\n" +
               ");";
    }

    public static String getDDLSpieltage(){
        //Saison;Spieltag;Datum;H/A;Gegner;Ergebnis;Punkte;Platz;Geschossen;Kassiert
        return "CREATE TABLE IF NOT EXISTS spieltage (\n" +
               "    id SERIAL PRIMARY KEY,\n" +
               "    saison VARCHAR(10) NOT NULL,\n" +
               "    spieltag INT NOT NULL,\n" +
               "    datum VARCHAR(20) NOT NULL,\n" +
               "    ha VARCHAR(5) NOT NULL,\n" +
               "    gegner VARCHAR(100) NOT NULL,\n" +
               "    ergebnis VARCHAR(10) NOT NULL,\n" +
               "    punkte INT NOT NULL,\n" +
               "    platz INT NOT NULL,\n" +
               "    geschossen INT NOT NULL,\n" +
               "    kassiert INT NOT NULL\n" +
               ");";
    }

    public static String getDDLSpielerEinsatz(){
        //Saison;Spiel;Nachname;Vorname;Einsatz;Gruppe;Punkte;Spielminuten;Datum;H/A;Ergebnis;Gegner
        return "CREATE TABLE spielereinsaetze (\n" +
               "    id SERIAL PRIMARY KEY,\n" +
               "    saison VARCHAR(10) NOT NULL,\n" +
               "    spiel INT NOT NULL,\n" +
               "    nachname VARCHAR(100) NOT NULL,\n" +
               "    vorname VARCHAR(100) NOT NULL,\n" +
               "    einsatz VARCHAR(50) NOT NULL,\n" +
               "    gruppe VARCHAR(50) NOT NULL,\n" +
               "    punkte INT NOT NULL,\n" +
               "    spielminuten INT NOT NULL,\n" +
               "    datum VARCHAR(20) NOT NULL,\n" +
               "    ha VARCHAR(5) NOT NULL,\n" +
               "    ergebnis VARCHAR(20) NOT NULL,\n" +
               "    gegner VARCHAR(100) NOT NULL\n" +
               ");";
    }
}
