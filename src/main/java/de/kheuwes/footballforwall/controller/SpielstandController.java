package de.kheuwes.footballforwall.controller;

import de.kheuwes.footballforwall.model.Spielstand;
import de.kheuwes.footballforwall.service.SpielstandService;
import de.kheuwes.utils.ClipUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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
    public Spielstand updateSpielstand(@RequestBody Spielstand spielstand) throws IOException {
        ClipUtils clipUtils = new ClipUtils(spielstand);
        clipUtils.playSoundfiles();
        return spielstandService.saveSpielstand(spielstand);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        spielstandService.deleteSpielstand(id);
        return ResponseEntity.ok().build();
    }
}
