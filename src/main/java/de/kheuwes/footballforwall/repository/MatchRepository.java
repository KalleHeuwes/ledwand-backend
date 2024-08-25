package de.kheuwes.footballforwall.repository;

import de.kheuwes.footballforwall.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
