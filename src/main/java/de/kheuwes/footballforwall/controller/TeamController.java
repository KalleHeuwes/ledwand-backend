package de.kheuwes.footballforwall.controller;


import de.kheuwes.footballforwall.model.Team;
import de.kheuwes.footballforwall.service.StatusService;
import de.kheuwes.footballforwall.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private StatusService statusService;

    @Autowired
    private TeamService teamService;

    @GetMapping
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        Optional<Team> team = teamService.getTeamById(id);
        return team.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

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

    @PostMapping
    public Team createTeam(@RequestBody Team team) {
        return teamService.saveTeam(team);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.ok().build();
    }
}
