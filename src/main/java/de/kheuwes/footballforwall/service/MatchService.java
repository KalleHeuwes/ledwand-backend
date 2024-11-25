package de.kheuwes.footballforwall.service;

import de.kheuwes.footballforwall.model.Match;
import de.kheuwes.footballforwall.model.MatchDay;
import de.kheuwes.footballforwall.model.Player;
import de.kheuwes.footballforwall.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchService {
    private MatchDay matchDay = null;
    private String anpfiff = null;
    private String hz = null;
    private int nachspielzeit = -1;

    @Autowired
    private MatchRepository matchRepository;

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public Optional<Match> getMatchById(Long id) {
        return matchRepository.findById(id);
    }

    public Match saveMatch(Match match) {
        return matchRepository.save(match);
    }

    public void deleteMatch(Long id) {
        matchRepository.deleteById(id);
    }

    public MatchDay readMatchDay(String filename) {
        this.matchDay = new MatchDay();
        this.matchDay.init(filename);
        setAnpfiff("");
        setHz("");
        setNachspielzeit(0);
        return this.matchDay;
    }

    public Player getSpieler(int nr){
        return this.matchDay.getSpieler(nr);
    }

    public MatchDay getMatchDay() {
        return this.matchDay;
    }

    public String getAnpfiff() {
        return anpfiff;
    }

    public void setAnpfiff(String anpfiff) {
        this.anpfiff = anpfiff;
    }

    public String getHz() {
        return hz;
    }

    public void setHz(String hz) {
        this.hz = hz;
    }
    
    public int getNachspielzeit() {
        return nachspielzeit;
    }

    public void setNachspielzeit(int nachspielzeit) {
        this.nachspielzeit = nachspielzeit;
    }
}

