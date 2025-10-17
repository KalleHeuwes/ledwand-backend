package de.kheuwes.footballforwall.service;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import de.kheuwes.footballforwall.configuration.ExternalServiceProperties;

@Component
public class DocumentsService {
    private ExternalServiceProperties properties;
    
    public DocumentsService(ExternalServiceProperties properties) {
        this.properties = properties;
    }

    public String getDocumentsRootPath() {
        return this.properties.getDocumentsRootPath();
    }

    public List<Path> getSaisonfolders() {
        return FolderLister.getDirectSubfolders(getDocumentsRootPath()).stream().filter(
            path -> path.getFileName().toString().startsWith("Saison")).collect(Collectors.toList());
    }

    public List<Path> getDocsForSaisonAndSpieltag(String typ, String saison, String spieltag) {
        return FolderLister.getDocsForSaisonAndSpieltag(typ, getDocumentsRootPath(), saison, spieltag);
    }
}
