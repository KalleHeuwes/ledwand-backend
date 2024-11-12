package de.kheuwes.footballforwall.service;

import de.kheuwes.footballforwall.model.Match;
import de.kheuwes.footballforwall.model.MatchDay;
import de.kheuwes.footballforwall.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchService {
    private MatchDay matchDay = null;

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
        return this.matchDay;
    }

    public MatchDay getMatchDay() {
        return this.matchDay;
    }
}

