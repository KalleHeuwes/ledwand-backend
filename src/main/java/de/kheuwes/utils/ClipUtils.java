package de.kheuwes.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.springframework.core.io.ClassPathResource;

import de.kheuwes.footballforwall.model.Spielstand;

public class ClipUtils {
    List<AudioInputStream> clipList = new ArrayList<>();
    public ClipUtils(Spielstand spielstand){
        AudioInputStream ais = null;
        String fileName = ("H".equalsIgnoreCase(spielstand.getHg()) 
        ? "static/sounds/TorHeim.wav" 
        : "static/sounds/TorGast.wav") ;
        ais = getAudioInputStream(fileName);
        if(ais != null) clipList.add(ais);
        clipList.add(getAudioInputStream("static/sounds/Zahl_0" + spielstand.getHeim() + ".wav"));
        clipList.add(getAudioInputStream("static/sounds/Zu.wav"));
        clipList.add(getAudioInputStream("static/sounds/Zahl_0" + spielstand.getGast() + ".wav"));
    }

    public void playSoundfiles(){
        Clip clip = null;

        for (AudioInputStream audioInputStream : clipList) {
            try {
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.setFramePosition(0);
                clip.start();
                while(clip.getMicrosecondLength() != clip.getMicrosecondPosition())
                {
                }
                clip.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public AudioInputStream getAudioInputStream(String fileName){
        AudioInputStream sound = null;
        File file = null;
        try {
            file = new ClassPathResource(fileName).getFile();
            if (file.exists()) {
                sound = AudioSystem.getAudioInputStream(file);
                return sound;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    

}
