package de.kheuwes.footballforwall.service;

import de.kheuwes.footballforwall.model.MatchDay;
import de.kheuwes.footballforwall.model.Player;
import de.kheuwes.footballforwall.model.Spielstand;

import org.springframework.stereotype.Service;

@Service
public class MatchService {
    private MatchDay matchDay = null;
    private Spielstand spielstand = new Spielstand(0, 0);
    private String anpfiff = null;
    private String hz = null;
    private int nachspielzeit = -1;

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

    public Spielstand getSpielstand() {
        return spielstand;
    }

    public void setSpielstand(Spielstand spielstand) {
        this.spielstand = spielstand;
    }
    
}

