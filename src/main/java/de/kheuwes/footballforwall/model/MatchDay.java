package de.kheuwes.footballforwall.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MatchDay {
    private String datum = "";
    private String gegner = "";
    private String gegnerBild = "";

    private List<Player> startelf = new ArrayList<Player>();
    private List<Player> bank = new ArrayList<Player>();

    public MatchDay init(String inFilename){
        String filename = "/" + inFilename.replace("-", "/");
        String modus = "";
        String[] items = null;
        System.out.println("init..." + filename);
        try {
            InputStream is = MatchDay.class.getResourceAsStream(filename);
            InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            for (String line; (line = reader.readLine()) != null;) {
                //System.out.println(line);
                items = line.split(";");
                if(items.length > 1){
                    if("Datum".equalsIgnoreCase(items[1])){
                        this.datum = items[2];
                    }
                    if("Gegner".equalsIgnoreCase(items[1])){
                        this.gegner = items[2];
                    }
                    if("Bild".equalsIgnoreCase(items[1])){
                        this.gegnerBild = items[2];
                    }
                    if("S".equalsIgnoreCase(modus)){
                        this.startelf.add(new Player(items[2], items[1], Integer.parseInt( items[0]), "", ""));
                    }
                    if("B".equalsIgnoreCase(modus)){
                        this.bank.add(new Player(items[2], items[1], Integer.parseInt( items[0]), "", ""));
                    }
                }

                if("#Startelf".equalsIgnoreCase(items[0])){
                    modus = "S";
                }
                if("#Bank".equalsIgnoreCase(items[0])){
                    modus = "B";
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            System.err.println(e.getLocalizedMessage());
        }
        return this;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getGegner() {
        return gegner;
    }

    public void setGegner(String gegner) {
        this.gegner = gegner;
    }

    public String getGegnerBild() {
        return gegnerBild;
    }

    public void setGegnerBild(String gegnerBild) {
        this.gegnerBild = gegnerBild;
    }

    public List<Player> getStartelf() {
        return startelf;
    }

    public void setStartelf(List<Player> startelf) {
        this.startelf = startelf;
    }

    public List<Player> getBank() {
        return bank;
    }

    public void setBank(List<Player> bank) {
        this.bank = bank;
    }
}
