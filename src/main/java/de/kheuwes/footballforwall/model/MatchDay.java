package de.kheuwes.footballforwall.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MatchDay {
    private String datum = "";
    private String gegner = "";
    private String trainer = "Daniel Apke";
    private String gegnerBild = "";
    private String bildPattern = "";
    private String design = "default";
    private int aufstellungNr = -1;

    private List<Player> startelf = new ArrayList<Player>();
    private List<Player> bank = new ArrayList<Player>();
    private List<String> spielerstatistikList = new ArrayList<String>();
    private List<String> halbzeitConfig = new ArrayList<String>();

    public List<String> readHalbzeitConfig(){
        List<String> ret = new ArrayList<String>();
        String filename = "";
        try {
            filename = "C:\\temp\\halbzeit.csv";
            System.out.println("Lese Halbzeit-Config aus " + filename);
            File initialFile = new File(filename);
            InputStream is = new FileInputStream(initialFile);
            InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            for (String line; (line = reader.readLine()) != null;) {
                ret.add(line);                
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            System.err.println(e.getLocalizedMessage());
        }
        return ret;
    }

    public List<String> getSpielerstatistik(){
        List<String> ret = new ArrayList<String>();
        String filename = "";
        try {
            filename = "C:\\temp\\statistik.csv";
            System.out.println("Lese Statistik aus " + filename);
            File initialFile = new File(filename);
            InputStream is = new FileInputStream(initialFile);
            InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            for (String line; (line = reader.readLine()) != null;) {
                ret.add(line);                
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            System.err.println(e.getLocalizedMessage());
        }
        return ret;
    }

    public Player getSpieler(int nr){
        for (Player player : startelf) {
            if(player.getNumber() == nr){
                return player;
            }
        }
        for (Player player : bank) {
            if(player.getNumber() == nr){
                return player;
            }
        }
        return null;
    }

    public MatchDay init(String inFilename){
        String filename = "/" + inFilename.replace("-", "/");
        String modus = "";
        String[] items = null;
        Player player = null;
        System.out.println("init..." + filename);
        try {
            this.spielerstatistikList = getSpielerstatistik();
            this.halbzeitConfig = readHalbzeitConfig();
            filename = "C:\\temp\\spieltag.csv";
            System.out.println("Lese Spieltag aus " + filename);
            File initialFile = new File(filename);
            InputStream is = new FileInputStream(initialFile);
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
                    if("BildPattern".equalsIgnoreCase(items[1])){
                        this.bildPattern = items[2];
                    }                    
                    if("Trainer".equalsIgnoreCase(items[1])){
                        this.trainer = items[2];
                    }
                    if("S".equalsIgnoreCase(modus)){
                        player = new Player(items[2], items[1], Integer.parseInt( items[0]), "", 
                            this.bildPattern.replace("<VN>", items[1]).replace("<NN>", items[2]));
                        addStatistics(spielerstatistikList, player);
                        this.startelf.add(player);
                    }
                    if("B".equalsIgnoreCase(modus)){
                        player = new Player(items[2], items[1], Integer.parseInt( items[0]), "", 
                            this.bildPattern.replace("<VN>", items[1]).replace("<NN>", items[2]));
                        addStatistics(spielerstatistikList, player);
                        this.bank.add(player);
                    }
                }

                if("#Startelf".equalsIgnoreCase(items[0])){
                    modus = "S";
                }
                if("#Bank".equalsIgnoreCase(items[0])){
                    modus = "B";
                }
            }
            reader.close();
            
        } catch (Exception e) {
            System.out.println("An error occurred.");
            System.err.println(e.getLocalizedMessage());
        }
        System.out.println(String.format("Spiel am %s gegen %s, Bild %s", this.datum, this.gegner, this.gegnerBild));
        return this;
    }

    private void addStatistics(List<String> stats, Player player){
        String[] items = null;
        for (String string : stats) {
            items = string.split(";");
            if(items[0].equalsIgnoreCase(player.getFirstName()) && items[1].equalsIgnoreCase(player.getName())){
                player.setPlayerStatistics(new PlayerStatistics(Integer.parseInt(items[3]), Integer.parseInt(items[2]), Integer.parseInt(items[4]), ""));
            }
        }
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

    public String getBildPattern() {
        return bildPattern;
    }

    public void setBildPattern(String bildPattern) {
        this.bildPattern = bildPattern;
    }

    public int getAufstellungNr() {
        return aufstellungNr;
    }

    public void setAufstellungNr(int aufstellungNr) {
        this.aufstellungNr = aufstellungNr;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public List<String> getHalbzeitConfig() {
        return halbzeitConfig;
    }

    
}
