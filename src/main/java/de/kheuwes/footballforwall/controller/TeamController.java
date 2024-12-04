package de.kheuwes.footballforwall.controller;


import de.kheuwes.footballforwall.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private StatusService statusService;

    @GetMapping("/spielerwechsel")
    public String getSpielerwechsel() {
        return statusService.getSpielerwechsel();
    }
    @PostMapping("/spielerwechsel/{hg}/{raus}/{rein}")
    public String setSpielerwechsel(@PathVariable String hg, @PathVariable Long raus, @PathVariable Long rein) {
        statusService.setStatusKennzeichen("W");
        statusService.setSpielerwechsel(hg+ "|" + raus + "|" + rein);
        System.out.println(statusService.getSpielerwechsel());
        return statusService.getSpielerwechsel();
    }
}
