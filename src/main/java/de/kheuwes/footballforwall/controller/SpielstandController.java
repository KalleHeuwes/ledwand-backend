package de.kheuwes.footballforwall.controller;

import de.kheuwes.footballforwall.model.Spielstand;
import de.kheuwes.footballforwall.model.Statuseintrag;
import de.kheuwes.footballforwall.service.MatchService;
import de.kheuwes.footballforwall.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/spielstand")
public class SpielstandController {

    @Autowired
    private StatusService statusService;

    @Autowired
    private MatchService matchService;

    @GetMapping("/string")
    public String getSpielstandAsString() {
        Spielstand stand = matchService.getSpielstand();
        return stand.shortString();
    }

    @GetMapping("/laufschrift")
    public String getLaufschrift() {
        return statusService.getLaufschrift();
    }

    @PostMapping("/torfueruns")
    public Spielstand torFuerUns(@RequestBody Statuseintrag statuseintrag) {
        return torVerbuchen(statuseintrag, 1,0, "H");
    }

    @PostMapping("/torfuergast")
    public Spielstand torFuerGast(@RequestBody Statuseintrag statuseintrag) {
        return torVerbuchen(statuseintrag, 0,1, "G");
    }

    private Spielstand torVerbuchen(Statuseintrag statuseintrag, int heim,int gast, String hg){
        Spielstand spielstand = matchService.getSpielstand();
        spielstand.setHeim(spielstand.getHeim() + heim);
        spielstand.setGast(spielstand.getGast() + gast);
        spielstand.setHg(hg);
        spielstand.setTsNummer(statuseintrag.getRueckennummer());
        spielstand.setSpielername(statuseintrag.getSpielername());
        statusService.saveStatuseintrag(statuseintrag);
        statusService.setStatusKennzeichen("T");
        System.out.println(statuseintrag);
        System.out.println(spielstand);
        return spielstand;
    }

    @PatchMapping
    public Spielstand updateSpielstand(@RequestBody Spielstand spielstand) throws IOException {
        if("H".equalsIgnoreCase(spielstand.getHg().toUpperCase()) || 
           "G".equalsIgnoreCase(spielstand.getHg().toUpperCase()))
           {
            // System.out.println("Neuen Spielstand ansagen ...");
            // ClipUtils clipUtils = new ClipUtils(spielstand);
            // clipUtils.playSoundfiles();
        }
        System.out.println(spielstand);
        return spielstand;
    }
}
