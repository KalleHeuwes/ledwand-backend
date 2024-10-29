package de.kheuwes.footballforwall.controller;

import de.kheuwes.footballforwall.model.KeyValuePair;
import de.kheuwes.footballforwall.model.Spielstand;
import de.kheuwes.footballforwall.model.Statuseintrag;
import de.kheuwes.footballforwall.service.KeyValueService;
import de.kheuwes.footballforwall.service.SpielstandService;
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

    @Autowired
    private SpielstandService spielstandService;

    @GetMapping
    public List<Statuseintrag> getAllStatuseintraege() {
        return statusService.getAllStatuseintraege();
    }

    @GetMapping("/status")
    public String getStatusKz() {
        Spielstand spielstandAlt = getAktSpielstand();
        return spielstandAlt.getStatusKz();
    }

    @PostMapping("/resetstatus")
    public String setStatusKz() {
        Spielstand spielstandAlt = getAktSpielstand();
        setStatusKennzeichen("");
        return spielstandAlt.getStatusKz();
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

    @PostMapping("/nachspielzeit/{nachspielzeit}")
    public KeyValuePair setNachspielzeit(@PathVariable String nachspielzeit) {
        return keyValueService.saveKeyValuePair(new KeyValuePair("Nachspielzeit", nachspielzeit));
    }

    @GetMapping("/anpfiff")
    public String getAnpfiff() {
        String ret = "";
        List<KeyValuePair> kvs = keyValueService.getAllKeyValuePairs();
        for (KeyValuePair kvp : kvs) {
            if("Anpfiff Hz 1".equalsIgnoreCase(kvp.getKeyName())){
                ret = "{\"hz\": 1, \"uhrzeit\": \"" + kvp.getValueStr() + "\"}";
            }            
            if("Anpfiff Hz 2".equalsIgnoreCase(kvp.getKeyName())){
                ret = "{\"hz\": 2, \"uhrzeit\": \"" + kvp.getValueStr() + "\"}";
            }
        }
        setStatusKennzeichen("A");
        return ret;
    }
    
    private Spielstand getAktSpielstand(){
        if(spielstandService.getAllSpielstands().size() == 0){
            spielstandService.createSpielstand();
        }
        return spielstandService.getAllSpielstands().get(0);
    }

    private void setStatusKennzeichen(String statusKz){
        Spielstand spielstandAlt = getAktSpielstand();
        spielstandAlt.setStatusKz(statusKz);
        spielstandService.saveSpielstand(spielstandAlt);
        System.out.println("Statuskennzeichen gesetzt auf: " + statusKz);
    }
}
