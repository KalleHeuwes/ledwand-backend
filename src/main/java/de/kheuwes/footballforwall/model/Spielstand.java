package de.kheuwes.footballforwall.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Spielstand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int heim;

    private int gast;

    // Constructors, getters, and setters
    public Spielstand() {}

    public Spielstand(int heim, int gast) {
        this.heim = heim;
        this.gast = gast;
    }

    public int getHeim() {
        return heim;
    }

    public void setHeim(int heim) {
        this.heim = heim;
    }

    public int getGast() {
        return gast;
    }

    public void setGast(int gast) {
        this.gast = gast;
    }

    public String toString(){
        return "" + this.heim + ':' + this.gast;
    }
    
}
