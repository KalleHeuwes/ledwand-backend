package de.kheuwes.footballforwall.controller;

import de.kheuwes.footballforwall.model.Spielstand;
import de.kheuwes.footballforwall.service.SpielstandService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/spielstand")
public class SpielstandController {

    @Autowired
    private SpielstandService spielstandService;

    @GetMapping
    public List<Spielstand> getAllMatches() {
        return spielstandService.getAllSpielstands();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Spielstand> getSpielstandById(@PathVariable Long id) {
        Optional<Spielstand> match = spielstandService.getSpielstandById(id);
        return match.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Spielstand createSpielstand(@RequestBody Spielstand spielstand) {
        return spielstandService.saveSpielstand(spielstand);
    }
    @PatchMapping
    public Spielstand updateSpielstand(@RequestBody Spielstand spielstand) {
        return spielstandService.saveSpielstand(spielstand);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        spielstandService.deleteSpielstand(id);
        return ResponseEntity.ok().build();
    }
}
