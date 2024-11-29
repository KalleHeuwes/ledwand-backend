package de.kheuwes.footballforwall.model;

public enum Statuskennzeichen {
    A ( "Anpfiff"),
    H ( "Halbzeit");
    
    public final String label;

    private Statuskennzeichen(String label) {
        this.label = label;
    }
}
