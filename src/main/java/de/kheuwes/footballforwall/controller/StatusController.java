package de.kheuwes.footballforwall.controller;

import de.kheuwes.footballforwall.model.KeyValuePair;
import de.kheuwes.footballforwall.model.Statuseintrag;
import de.kheuwes.footballforwall.service.KeyValueService;
import de.kheuwes.footballforwall.service.StatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/status")
public class StatusController {
    @Autowired
    private KeyValueService keyValueService;

    @Autowired
    private StatusService statusService;

    @GetMapping
    public List<Statuseintrag> getAllStatuseintraege() {
        return statusService.getAllStatuseintraege();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Statuseintrag> getStatuseintragById(@PathVariable Long id) {
        Optional<Statuseintrag> statuseintrag = statusService.getStatuseintragById(id);
        return statuseintrag.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Statuseintrag createStatuseintrag(@RequestBody Statuseintrag statuseintrag) {
        return statusService.saveStatuseintrag(statuseintrag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatuseintrag(@PathVariable Long id) {
        statusService.deleteStatuseintrag(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/anpfiff/{hz}/{uhrzeit}")
    public KeyValuePair setAnpfiff(@PathVariable String hz, @PathVariable String uhrzeit) {
        return keyValueService.saveKeyValuePair(new KeyValuePair("Anpfiff Hz " + hz, uhrzeit));
    }
}
