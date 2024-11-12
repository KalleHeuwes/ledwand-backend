package de.kheuwes.footballforwall.controller;

import de.kheuwes.footballforwall.model.Match;
import de.kheuwes.footballforwall.model.MatchDay;
import de.kheuwes.footballforwall.service.MatchService;
import de.kheuwes.footballforwall.service.StatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @Autowired
    private StatusService statusService;

    @GetMapping
    public List<Match> getAllMatches() {
        return matchService.getAllMatches();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Match> getMatchById(@PathVariable Long id) {
        Optional<Match> match = matchService.getMatchById(id);
        return match.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Match createMatch(@RequestBody Match match) {
        return matchService.saveMatch(match);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        matchService.deleteMatch(id);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/read/{filename}")
    public MatchDay readMatchDay(@PathVariable String filename) {   
        matchService.readMatchDay(filename);  
        statusService.setStatusKennzeichen("S");   
        return matchService.getMatchDay();
    }
    
    @GetMapping("/matchday/short")
    public String getMatchDay() {
        
        MatchDay matchDay = matchService.getMatchDay();
        String ret = String.format("%s|%s|%s", matchDay.getGegner(), matchDay.getDatum(), matchDay.getGegnerBild());
        return ret;
    }
}
