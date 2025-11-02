package de.kheuwes.footballforwall.repository.historie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.kheuwes.footballforwall.model.historie.SpielerEinsatz;
import de.kheuwes.footballforwall.model.historie.SpielerSaisonPerformance;

public interface SpielerEinsatzRepository extends JpaRepository<SpielerEinsatz, Long> {
    List<SpielerEinsatz> findBySaison(String saison);

    List<SpielerEinsatz> findBySaisonAndSpiel(String saison, Long spiel);

    List<SpielerEinsatz> findByNachnameAndVorname(String nachname, String vorname);

    List<SpielerEinsatz> findByNachnameAndVornameAndSaison(String nachname, String vorname, String saison);

    List<SpielerEinsatz> findByNachnameContainingIgnoreCase(String teilname);

    @Query(value = "SELECT spielername, saison, gesamt_minuten " +
                   "FROM meine_spielzeit_view " + // DIREKTE Referenz zur View
                   "WHERE spielername = :name", 
           nativeQuery = true)
    List<Object[]> queryViewNative(String name);

    @Query("SELECT NEW de.kheuwes.footballforwall.model.historie.SpielerSaisonPerformance(" + 
           "e.saison, e.nachname, e.vorname, COUNT(e.saison), 0L, 0L) " +
           "FROM SpielerEinsatz e " + 
           "WHERE e.nachname = :nachname AND e.vorname = :vorname " +
           "GROUP BY e.saison, e.nachname, e.vorname") 
    List<SpielerSaisonPerformance> findDistinctSaisonsBySpieler(
        @Param("nachname") String nachname,
        @Param("vorname") String vorname
    );

    @Query(value = "SELECT DISTINCT CONCAT(nachname, ', ', vorname) AS spielername " +
                   "FROM SPIELEREINSAETZE" , 
           nativeQuery = true)
    List<String> findSpielernamen();
}
