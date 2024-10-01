package de.kheuwes.footballforwall.controller;

import de.kheuwes.footballforwall.model.KeyValuePair;
import de.kheuwes.footballforwall.service.KeyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/keyValuePairs")
public class KeyValueController {
    @Autowired
    private KeyValueService keyValueService;

    @GetMapping
    public List<KeyValuePair> getAllKeyValuePairs() {
        return keyValueService.getAllKeyValuePairs();
    }

    @PostMapping("/{key}/{value}")
    public KeyValuePair setKeyValuePair(@PathVariable String key, @PathVariable String value) {
        return keyValueService.saveKeyValuePair(new KeyValuePair(key, value));
    }    
}
