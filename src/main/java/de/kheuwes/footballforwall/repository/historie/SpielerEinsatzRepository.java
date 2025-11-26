package de.kheuwes.footballforwall.repository.historie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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

@Modifying
@Transactional
@Query(
    value = "DELETE FROM spielereinsaetze", // <-- Die Basistabelle im SQL verwenden
    nativeQuery = true // <-- Wichtig: Dies erzwingt die Ausführung von nativem SQL
)
void bulkDeleteAll();

    @Query("SELECT NEW de.kheuwes.footballforwall.model.historie.SpielerSaisonPerformance(" + 
           "e.nachname, e.vorname, e.saison, e.liga, e.spieleSpieler, e.spieleTeam, e.spieleAnteil" +
           ", e.spielminutenSpieler, e.spielminutenTeam, e.spielminutenAnteil" +
           ", e.punkteSpieler, e.punkteTeam, e.punkteAnteil, e.spielMin, e.spielMax, e.bemerkungen) " +
           "FROM SpielerSaisonPerformance e " + 
           "WHERE e.nachname = :nachname AND e.vorname = :vorname ") 
    List<SpielerSaisonPerformance> querySpielerPerformance(
        @Param("nachname") String nachname,
        @Param("vorname") String vorname
    );

    @Query(value = "SELECT DISTINCT CONCAT(nachname, ', ', vorname) AS spielername " +
                   "FROM SPIELEREINSAETZE" , 
           nativeQuery = true)
    List<String> findSpielernamen();
}
