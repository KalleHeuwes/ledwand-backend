package de.kheuwes.footballforwall.repository.historie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.kheuwes.footballforwall.model.historie.SpielerEinsatz;

public interface SpielerEinsatzRepository extends JpaRepository<SpielerEinsatz, Long> {
    List<SpielerEinsatz> findBySaison(String saison);

    @Query("SELECT se FROM SpielerEinsatz se JOIN se.spiele map WHERE se.saison = :saison AND KEY(map) = :spielnummer")
    List<SpielerEinsatz> findBySaisonAndSpielnummer(@Param("saison") String saison, @Param("spielnummer") Long spielnummer);

    List<SpielerEinsatz> findByNachnameAndVorname(String nachname, String vorname);

    List<SpielerEinsatz> findByNachnameContainingIgnoreCase(String teilname);
}
