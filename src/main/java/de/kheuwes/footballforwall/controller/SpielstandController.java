package de.kheuwes.footballforwall.controller;

import de.kheuwes.footballforwall.model.Spielstand;
import de.kheuwes.footballforwall.model.Statuseintrag;
import de.kheuwes.footballforwall.service.SpielstandService;
import de.kheuwes.footballforwall.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/spielstand")
public class SpielstandController {

    @Autowired
    private SpielstandService spielstandService;

    @Autowired
    private StatusService statusService;

    @GetMapping
    public List<Spielstand> getAllMatches() {
        return spielstandService.getAllSpielstands();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Spielstand> getSpielstandById(@PathVariable Long id) {
        Optional<Spielstand> match = spielstandService.getSpielstandById(id);
        return match.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
        Spielstand spielstandAlt = spielstandService.getSpielstand();
        spielstandAlt.setHeim(spielstandAlt.getHeim() + heim);
        spielstandAlt.setGast(spielstandAlt.getGast() + gast);
        spielstandAlt.setHg(hg);
        spielstandAlt.setTsNummer(statuseintrag.getRueckennummer());
        statusService.saveStatuseintrag(statuseintrag);
        statusService.setStatusKennzeichen("T");
        System.out.println(statuseintrag);
        System.out.println(spielstandAlt);
        return spielstandService.saveSpielstand(spielstandAlt);
    }

    @PostMapping
    public Spielstand createSpielstand(@RequestBody Spielstand spielstand) {
        return spielstandService.saveSpielstand(spielstand);
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
        return spielstandService.saveSpielstand(spielstand);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        spielstandService.deleteSpielstand(id);
        return ResponseEntity.ok().build();
    }
}
