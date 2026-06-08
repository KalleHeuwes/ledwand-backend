package de.kheuwes.footballforwall.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*
         * Erklärt dem Server: Jede Anfrage, die mit "/api/static-files/" beginnt,
         * soll im lokalen Dateisystem unter "V:/05/" gesucht werden.
         * * Beispiel-Mapping:
         * URL: http://localhost:8080/api/static-files/Saison2526/Fotos/Saison2526_06_Schomaker,J.png
         * Pfad: V:/05/Saison2526/Fotos/Saison2526_06_Schomaker,J.png
         */
        registry.addResourceHandler("/api/static-files/**")
                // Wichtig: Das Präfix "file:" zeigt an, dass es sich um ein lokales Verzeichnis handelt.
                // Der abschließende Slash "/" ist zwingend erforderlich!
                .addResourceLocations("file:V:/05/")
                .setCachePeriod(3600) // Optional: Cache für 1 Stunde aktivieren
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}