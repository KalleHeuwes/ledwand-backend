package de.kheuwes.footballforwall.repository.historie;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import de.kheuwes.footballforwall.model.historie.Abschlusstabelleneintrag;

// Erbt alle Standard-DB-Methoden (save, findById, findAll, etc.)
public interface AbschlusstabellenRepository extends JpaRepository<Abschlusstabelleneintrag, Long> {

    /**
     * Ermöglicht die Abfrage für die REST-API.
     * Spring generiert die SQL-Abfrage automatisch basierend auf dem Methodennamen.
     * SELECT * FROM abschlusstabelleneintrag WHERE saison = ?
     */
    List<Abschlusstabelleneintrag> findBySaison(String saison);
}