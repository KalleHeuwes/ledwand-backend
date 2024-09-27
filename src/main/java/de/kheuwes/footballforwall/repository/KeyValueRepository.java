package de.kheuwes.footballforwall.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.kheuwes.footballforwall.model.KeyValuePair;

public interface KeyValueRepository extends JpaRepository<KeyValuePair, Long> {

    long deleteByKeyName(String keyName);

}

