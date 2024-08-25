package de.kheuwes.footballforwall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import de.kheuwes.footballforwall.model.Spielstand;

public interface SpielstandRepository extends JpaRepository<Spielstand, Long> {

}
