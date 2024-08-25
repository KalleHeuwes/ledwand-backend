package de.kheuwes.footballforwall.repository;



import de.kheuwes.footballforwall.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
