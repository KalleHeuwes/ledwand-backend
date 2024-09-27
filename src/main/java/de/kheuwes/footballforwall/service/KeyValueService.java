package de.kheuwes.footballforwall.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.kheuwes.footballforwall.model.KeyValuePair;
import de.kheuwes.footballforwall.repository.KeyValueRepository;

@Service
@Transactional
public class KeyValueService {

    @Autowired
    private KeyValueRepository keyValueRepository;

    public List<KeyValuePair> getAllKeyValuePairs() {
        return keyValueRepository.findAll();
    }

    public Optional<KeyValuePair> getKeyValuePairById(Long id) {
        return keyValueRepository.findById(id);
    }

    public KeyValuePair saveKeyValuePair(KeyValuePair match) {
        keyValueRepository.deleteByKeyName(match.getKeyName());
        return keyValueRepository.save(match);
    }

}
