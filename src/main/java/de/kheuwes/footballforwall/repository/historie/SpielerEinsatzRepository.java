package de.kheuwes.footballforwall.repository.historie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.kheuwes.footballforwall.model.historie.SpielerEinsatz;

public interface SpielerEinsatzRepository extends JpaRepository<SpielerEinsatz, Long> {
    List<SpielerEinsatz> findBySaison(String saison);

    List<SpielerEinsatz> findBySaisonAndSpiel(String saison, Long spiel);

    List<SpielerEinsatz> findByNachnameAndVorname(String nachname, String vorname);

    List<SpielerEinsatz> findByNachnameContainingIgnoreCase(String teilname);

    @Query(value = "SELECT spielername, saison, gesamt_minuten " +
                   "FROM meine_spielzeit_view " + // DIREKTE Referenz zur View
                   "WHERE spielername = :name", 
           nativeQuery = true)
    List<Object[]> queryViewNative(String name);

    @Query(value = "SELECT DISTINCT CONCAT(nachname, ', ', vorname) AS spielername " +
                   "FROM SPIELEREINSAETZE" , 
           nativeQuery = true)
    List<String> findSpielernamen();
}
