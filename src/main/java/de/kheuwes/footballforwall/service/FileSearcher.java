package de.kheuwes.footballforwall.service;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileSearcher {
    public static void main(String[] args) {
        Path startPath = Paths.get("V:\\05");
        String searchWord = "tillmann";

        try (Stream<Path> stream = Files.walk(startPath)) {
            List<String> result = stream
                .filter(Files::isRegularFile)
                // Filtert basierend auf dem Dateinamen, behält aber das Path-Objekt
                .filter(path -> path.getFileName().toString().toLowerCase().contains(searchWord.toLowerCase()))
                // Wandelt den gesamten Pfad in einen String um
                .map(path -> path.toAbsolutePath().toString())
                .collect(Collectors.toList());

            // Ausgabe der vollständigen Pfade
            result.forEach(System.out::println);
            
        } catch (IOException e) {
            System.err.println("Fehler: " + e.getMessage());
        }
    }
}
