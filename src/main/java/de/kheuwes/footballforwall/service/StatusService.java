package de.kheuwes.footballforwall.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.kheuwes.footballforwall.model.Statuseintrag;
import de.kheuwes.footballforwall.repository.StatusRepository;

@Service
public class StatusService {
    @Autowired
    private StatusRepository repository;

    
    public List<Statuseintrag> getAllStatuseintraege() {
        return repository.findAll();
    }

    public String getLaufschrift(){
        String ret = "";
        int toreHeim = 0, toreGast = 0;
        for(Statuseintrag statuseintrag: getAllStatuseintraege()){
            ret+= "(" + statuseintrag.getSpielminute() + ".)";
            if("T".equalsIgnoreCase(statuseintrag.getTyp())){
                if("H".equalsIgnoreCase(statuseintrag.getHg())){ toreHeim++; }
                if("G".equalsIgnoreCase(statuseintrag.getHg())){ toreGast++; }
                ret+= " " + toreHeim + ":" + toreGast;
                if("H".equalsIgnoreCase(statuseintrag.getHg())){ 
                    ret+= " " + statuseintrag.getSpielername() + " (" + statuseintrag.getRueckennumer() + ") ";
                }
            }
        }
        return ret+="; ";
    }

    public Optional<Statuseintrag> getStatuseintragById(Long id) {
        return repository.findById(id);
    }

    public Statuseintrag saveStatuseintrag(Statuseintrag statuseintrag) {
        return repository.save(statuseintrag);
    }

    public void deleteStatuseintrag(Long id) {
        repository.deleteById(id);
    }
}