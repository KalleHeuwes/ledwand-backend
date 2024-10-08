package de.kheuwes.footballforwall.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.kheuwes.footballforwall.model.Spielstand;
import de.kheuwes.footballforwall.repository.SpielstandRepository;

@Service
public class SpielstandService {

    @Autowired
    private SpielstandRepository spielstandRepository;

    public List<Spielstand> getAllSpielstands() {
        return spielstandRepository.findAll();
    }

    public Optional<Spielstand> getSpielstandById(Long id) {
        return spielstandRepository.findById(id);
    }

    public Spielstand saveSpielstand(Spielstand spielstand) {
        System.out.println("Spielstand gespeichert: " + spielstand.toString());
        spielstandRepository.deleteAll();
        return spielstandRepository.save(spielstand);
    }

    public Spielstand createSpielstand() {
        System.out.println("Spielstand erzeugt");
        spielstandRepository.deleteAll();
        return spielstandRepository.save(new Spielstand(0, 0));
    }

    public void deleteSpielstand(Long id) {
        spielstandRepository.deleteById(id);
    }
}
