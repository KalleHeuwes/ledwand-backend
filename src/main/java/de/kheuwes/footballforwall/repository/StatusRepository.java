package de.kheuwes.footballforwall.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.kheuwes.footballforwall.model.Statuseintrag;

public interface StatusRepository  extends JpaRepository<Statuseintrag, Long> {

}
