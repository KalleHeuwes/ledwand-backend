package de.kheuwes.footballforwall.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class KeyValuePair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public String keyName = "";
    public String valueStr = "";

    
    @Override
    public String toString() {
        return "KeyValuePair [id=" + id + ", key=" + keyName + ", value=" + valueStr + "]";
    }

    
    public KeyValuePair() {
    }


    public KeyValuePair(String key, String value) {
        this.keyName = key;
        this.valueStr = value;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getKeyName() {
        return keyName;
    }
    public void setKeyName(String key) {
        this.keyName = key;
    }
    public String getValueStr() {
        return valueStr;
    }
    public void setValueStr(String value) {
        this.valueStr = value;
    }
}
