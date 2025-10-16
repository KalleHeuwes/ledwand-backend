package de.kheuwes.footballforwall.repository.historie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.kheuwes.footballforwall.model.historie.Spieltage;

import java.util.List;

@Repository
public interface SpieltageRepository extends JpaRepository<Spieltage, Long> {

    List<Spieltage> findBySaison(String saison);

    List<Spieltage> findByGegnerContainingIgnoreCase(String gegner);

    List<Spieltage> findByPlatz(int platz);

    Spieltage findBySaisonAndSpieltag(String saisonCalc, Long spieltag);
}
