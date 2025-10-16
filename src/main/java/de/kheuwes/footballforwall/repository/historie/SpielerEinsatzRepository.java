package de.kheuwes.footballforwall.repository.historie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.kheuwes.footballforwall.model.historie.SpielerEinsatz;

public interface SpielerEinsatzRepository extends JpaRepository<SpielerEinsatz, Long> {
    List<SpielerEinsatz> findBySaison(String saison);

    List<SpielerEinsatz> findBySaisonAndSpiel(String saison, Long spiel);

    List<SpielerEinsatz> findByNachnameAndVorname(String nachname, String vorname);

    List<SpielerEinsatz> findByNachnameContainingIgnoreCase(String teilname);
}
