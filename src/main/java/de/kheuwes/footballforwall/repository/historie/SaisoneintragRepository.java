package de.kheuwes.footballforwall.repository.historie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.kheuwes.footballforwall.model.historie.Saisoneintrag;

/**
 * Repository-Interface für die Saisoneintrag Entität.
 * Ermöglicht CRUD-Operationen (Create, Read, Update, Delete)
 * und einfache Abfragen auf der Datenbanktabelle.
 */
@Repository
public interface SaisoneintragRepository extends JpaRepository<Saisoneintrag, String> {

    /**
     * Beispiel für eine benutzerdefinierte Abfrage (optional):
     * Sucht alle Saisoneintrag-Einträge für eine bestimmte Liga.
     * Spring Data JPA generiert die Abfrage automatisch aus dem Methodennamen.
     *
     * @param liga Der Name der Liga (z.B. "Landesliga 4").
     * @return Eine Liste von Saisoneintrag-Objekten.
     */
    // List<Saisoneintrag> findByLiga(String liga);

    /**
     * Beispiel für eine benutzerdefinierte Abfrage (optional):
     * Sucht alle Saisons, in denen der Performanceindex über einem bestimmten Wert lag.
     *
     * @param performanceIndex Der Mindestwert für den Performanceindex.
     * @return Eine Liste von Saisoneintrag-Objekten.
     */
    // List<Saisoneintrag> findByPerformanceIndexGreaterThan(int performanceIndex);

}