package de.kheuwes.footballforwall.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.kheuwes.footballforwall.model.historie.FileItem;

public class FolderLister {
    public static String DOCS_ROOT_PATH = ""; // Beispielpfad, anpassen nach Bedarf

    public static List<Path> getDirectSubfolders(String startPathString) {
        Path startPath = Paths.get(startPathString);
        
        try (Stream<Path> stream = Files.list(startPath)) {
            
            // Filtern nach Verzeichnissen (isRegularFile() würde Dateien finden)
            return stream
                    .filter(Files::isDirectory) // Filtert nur Verzeichnisse
                    .collect(Collectors.toList());
                    
        } catch (IOException e) {
            e.printStackTrace();
            return List.of(); // Leere Liste im Fehlerfall
        }
    }

    public static List<FileItem> getDocsForSaisonAndSpieltag(String typ, String startPathString, String saison, String spieltag ) {
        String saisontmp = saison.replace("/", "");
        if(saisontmp.length() > 4) {
            saisontmp = saisontmp.substring(2, 6);
        }
        String t = startPathString + "Saison" + saisontmp + "\\" + typ;
        Path startPath = Paths.get(t);
        //V:\05\Saison2425\Dokumente\Saison2425_01_Spielbericht.pdf
        //final Pattern filePattern = Pattern.compile("Saison\\d{4}_\\d{2}_Spielbericht\\.pdf", Pattern.CASE_INSENSITIVE);
        final Pattern filePattern = Pattern.compile("Saison" + saisontmp + "_" + spieltag + ".*", Pattern.CASE_INSENSITIVE);
        
        try (Stream<Path> stream = Files.list(startPath)) {
            
            // Filtern nach Verzeichnissen (isRegularFile() würde Dateien finden)
            return stream
                    .filter(Files::isRegularFile) // Filtert nur Dateien
                    .filter(path -> {
                        String fileName = path.getFileName().toString();
                        Matcher matcher = filePattern.matcher(fileName);
                        return matcher.matches();
                    })
                    .sorted(Comparator.comparing(Path::getFileName))
                    .map(FolderLister::createFileItemFromPath)
                    .collect(Collectors.toList());
                    
        } catch (IOException e) {
            e.printStackTrace();
            return List.of(); // Leere Liste im Fehlerfall
        }
    }

    private static FileItem createFileItemFromPath(Path path) {
        String fileName = path.getFileName() != null ? path.getFileName().toString() : "";
        String filePath = path.toAbsolutePath().toString().replace(DOCS_ROOT_PATH, ".\\");
        String typ;
        System.out.println("path.toAbsolutePath().toString(): " + path.toAbsolutePath().toString());
        System.out.println("DOCS_ROOT_PATH: " + DOCS_ROOT_PATH);

        // Bestimmung des Typs ("FILE" oder "DIRECTORY") UNBEDINGT ZUERST MACHEN
        if (fileName.substring(fileName.lastIndexOf(".")).toLowerCase().equals(".pdf")) {
            typ = "PDF";
        } else if (fileName.substring(fileName.lastIndexOf(".")).toLowerCase().equals(".png")) {
            typ = "PNG";
        } else {
            typ = "OTHER"; // Für symbolische Links, etc.
        }

        //Saison2526_22_ herausschneiden
        fileName = fileName.length() > 14 ? fileName.substring(14) : fileName;
        fileName = fileName.indexOf(".") > 0 ? fileName.substring(0, fileName.lastIndexOf(".")) : fileName;


        return new FileItem(fileName, filePath, typ);
    }
}