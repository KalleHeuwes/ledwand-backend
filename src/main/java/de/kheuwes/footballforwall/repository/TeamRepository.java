package de.kheuwes.footballforwall.repository;

import de.kheuwes.footballforwall.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
