package de.kheuwes.footballforwall.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import de.kheuwes.footballforwall.configuration.ExternalServiceProperties;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/files")
public class FileDownloadController {

    // Basisverzeichnis, in dem Ihre Dateien gespeichert sind (z.B. /srv/data/files)
    private Path fileStorageLocation = Paths.get("V:\\05").toAbsolutePath().normalize();

    private final ExternalServiceProperties properties;

    public FileDownloadController(ExternalServiceProperties properties) {
        this.properties = properties;
        this.fileStorageLocation = Paths.get(this.properties.getDocumentsRootPath()).toAbsolutePath().normalize();
    }
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String filePath) {
        try {
            Path targetFile = this.fileStorageLocation.resolve(filePath).normalize();
            
            // WICHTIG: Sicherheitsprüfung (Path Traversal Prevention)
            // Stellt sicher, dass der angeforderte Pfad INNERHALB des Basisverzeichnisses liegt.
            if (!targetFile.startsWith(this.fileStorageLocation)) {
                return ResponseEntity.badRequest().body(null); // Oder 404 Not Found
            }
            
            Resource resource = new UrlResource(targetFile.toUri());

            if (resource.exists()) {
                String contentType = determineContentType(targetFile);

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        // Header "attachment" erzwingt Download, "inline" zeigt im Browser an
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    private String determineContentType(Path filePath) {
        try {
            return Files.probeContentType(filePath); // Java NIO kann den Typ oft automatisch bestimmen
        } catch (IOException e) {
            return "application/octet-stream";
        }
    }
}