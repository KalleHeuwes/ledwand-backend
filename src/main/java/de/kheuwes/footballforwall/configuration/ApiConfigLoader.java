package de.kheuwes.footballforwall.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import de.kheuwes.footballforwall.service.FolderLister;
import jakarta.annotation.PostConstruct;


@Configuration
public class ApiConfigLoader {

    @Value("${documents-root-path}")
    private String documentsRootPath;

    @PostConstruct
    public void init() {
        // Weist den injizierten Wert dem statischen Feld in der anderen Klasse zu
        FolderLister.DOCS_ROOT_PATH = documentsRootPath;

        System.out.println("Static DOCS_ROOT_PATH geladen: " + FolderLister.DOCS_ROOT_PATH);
    }
}