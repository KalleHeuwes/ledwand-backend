package de.kheuwes.footballforwall.controller;

import java.nio.file.Path;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.kheuwes.footballforwall.service.DocumentsService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class DocumentsController {
    private DocumentsService documentsService;
    public DocumentsController(DocumentsService documentsService) {
        this.documentsService = documentsService;
    }

    @GetMapping("/api/historie/documents/{saison}")
    public String getDocumentsRootPath() {
        return this.documentsService.getDocumentsRootPath();
    }

    @GetMapping("/api/historie/documents/saisons")
    public List<Path> getSaisonfolders() {
        return this.documentsService.getSaisonfolders();
    }

    @GetMapping("/api/historie/documents")
    public List<Path> getDocsForSaisonAndSpieltag(@RequestParam String typ, @RequestParam String saison, @RequestParam String spieltag) {
        return this.documentsService.getDocsForSaisonAndSpieltag(typ, saison, spieltag);
    }
}
