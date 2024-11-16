package de.kheuwes.footballforwall.model;

public class PlayerStatistics {
    private int spiele= -1;
    private int minuten = -1;
    private int tore = -1;
    private String imVereinSeit = "";

    
    public PlayerStatistics(int spiele, int minuten, int tore, String imVereinSeit) {
        this.spiele = spiele;
        this.minuten = minuten;
        this.tore = tore;
        this.imVereinSeit = imVereinSeit;
    }
    
    @Override
    public String toString() {
        return "PlayerStatistics [spiele=" + spiele + ", minuten=" + minuten + ", tore=" + tore + ", imVereinSeit="
                + imVereinSeit + "]";
    }
    public int getSpiele() {
        return spiele;
    }
    public void setSpiele(int spiele) {
        this.spiele = spiele;
    }
    public int getMinuten() {
        return minuten;
    }
    public void setMinuten(int minuten) {
        this.minuten = minuten;
    }
    public int getTore() {
        return tore;
    }
    public void setTore(int tore) {
        this.tore = tore;
    }
    public String getImVereinSeit() {
        return imVereinSeit;
    }
    public void setImVereinSeit(String imVereinSeit) {
        this.imVereinSeit = imVereinSeit;
    }
}
