package de.kheuwes.footballforwall.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class ExternalServiceProperties {
    // Bindet an 'documentsRootPath' oder 'documents-root-path' etc.
    private String documentsRootPath;

    public String getDocumentsRootPath() {
        return documentsRootPath;
    }

    public void setDocumentsRootPath(String documentsRootPath) {
        this.documentsRootPath = documentsRootPath;
    }
}
