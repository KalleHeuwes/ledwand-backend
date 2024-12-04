package de.kheuwes.footballforwall.controller;

import de.kheuwes.footballforwall.model.MatchDay;
import de.kheuwes.footballforwall.model.Player;
import de.kheuwes.footballforwall.model.Spielstand;
import de.kheuwes.footballforwall.service.MatchService;
import de.kheuwes.footballforwall.service.StatusService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @Autowired
    private StatusService statusService;

    @PostMapping("/read/{filename}")
    public MatchDay readMatchDay(@PathVariable String filename) {   
        matchService.readMatchDay(filename);  
        matchService.setSpielstand(new Spielstand(0, 0));
        statusService.deleteAll();
        
        statusService.setStatusKennzeichen("S");   
        return matchService.getMatchDay();
    }
    
    @GetMapping("/matchday/short")
    public String getMatchDay() {
        String ret = "";
        MatchDay matchDay = matchService.getMatchDay();
        if(matchDay != null){
            ret = String.format("%s|%s|%s", matchDay.getGegner(), matchDay.getDatum(), matchDay.getGegnerBild());
        }        
        return ret;
    }
        
    @GetMapping("/matchday/long")
    public MatchDay getMatchDayLong() {
        return matchService.getMatchDay();
    }     
        
    @GetMapping("/matchday/halbzeit-config")
    public List<String> getHalbzeitConfig() {
        return matchService.getMatchDay().getHalbzeitConfig();
    }     

    @GetMapping("/matchday/spieler/{nr}")
    public Player getSpieler(@PathVariable int nr) {
        return matchService.getSpieler(nr);
    }
    
    @PostMapping("/matchday/design/{design}")
    public String setDesign(@PathVariable String design) {   
        matchService.getMatchDay().setDesign(design);
        return matchService.getMatchDay().getDesign();
    }    
    
    @GetMapping("/matchday/design")
    public String getDesign() {
        if(matchService.getMatchDay() == null){
            return "default";
        }
        return matchService.getMatchDay().getDesign();
    }  
    @GetMapping("/aufstellung/naechster")
    public String getAufstellungNaechster() {
        if(matchService.getMatchDay() == null){
            return "-";
        }
        int nr = matchService.getMatchDay().getAufstellungNr();
        return String.valueOf(nr);    
    }
}
