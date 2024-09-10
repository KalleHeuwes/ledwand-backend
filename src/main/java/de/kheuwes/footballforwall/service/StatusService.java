package de.kheuwes.footballforwall.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import de.kheuwes.footballforwall.model.Statuseintrag;
import de.kheuwes.footballforwall.repository.StatusRepository;

public class StatusService {
    @Autowired
    private StatusRepository repository;

    
    public List<Statuseintrag> getAllStatuseintraege() {
        return repository.findAll();
    }

    public Optional<Statuseintrag> getStatuseintragById(Long id) {
        return repository.findById(id);
    }

    public Statuseintrag saveStatuseintrag(Statuseintrag statuseintrag) {
        return repository.save(statuseintrag);
    }

    public void deleteStatuseintrag(Long id) {
        repository.deleteById(id);
    }
}
