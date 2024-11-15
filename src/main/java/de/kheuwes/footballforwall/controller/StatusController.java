package de.kheuwes.footballforwall.controller;

import de.kheuwes.footballforwall.model.Statuseintrag;
import de.kheuwes.footballforwall.service.MatchService;
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
    private StatusService statusService;

    @Autowired
    private MatchService matchService;

    @GetMapping
    public List<Statuseintrag> getAllStatuseintraege() {
        return statusService.getAllStatuseintraege();
    }

    @GetMapping("/status")
    public String getStatusKennzeichen() {
        return statusService.getStatusKennzeichen();
    }

    @PostMapping("/resetstatus")
    public String resetstatus() {
        setStatusKennzeichen("");
        return statusService.getStatusKennzeichen();
    }

    private void setStatusKennzeichen(String statusKz){
        statusService.setStatusKennzeichen(statusKz);
        System.out.println("Statuskennzeichen gesetzt auf: " + statusKz);
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
    public String setAnpfiff(@PathVariable String hz, @PathVariable String uhrzeit) {
        setStatusKennzeichen("A");
        matchService.setAnpfiff(uhrzeit);
        matchService.setHz(hz);
        matchService.setNachspielzeit(0);
        return matchService.getHz() + "|" + matchService.getAnpfiff();
    }

    @PostMapping("/nachspielzeit/{nachspielzeit}")
    public int setNachspielzeit(@PathVariable Integer nachspielzeit) {
        setStatusKennzeichen("A");
        matchService.setNachspielzeit(nachspielzeit);
        System.out.println("Setze Nachspielzeit auf: " + matchService.getNachspielzeit());
        return matchService.getNachspielzeit();
    }
    
    @GetMapping("/nachspielzeit")
    public int getNachspielzeit() {
        return matchService.getNachspielzeit();
    }

    @GetMapping("/anpfiff")
    public String getAnpfiff() {
        return matchService.getHz() + "|" + matchService.getAnpfiff() + "|" + matchService.getNachspielzeit();
    }

}
