package com.example.Elearning.Impl;

import com.example.Elearning.Entity.Niveau;
import com.example.Elearning.Repository.NiveauRepository;
import com.example.Elearning.Service.NiveauService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NiveauServiceImpl implements NiveauService {

    private final NiveauRepository niveauRepository;

    public NiveauServiceImpl(NiveauRepository niveauRepository) {
        this.niveauRepository = niveauRepository;
    }

    @Override
    public List<Niveau> getAll() {
        return niveauRepository.findAll();
    }

    @Override
    public Niveau getById(Long id) {
        return niveauRepository.findById(id).orElseThrow();
    }

    @Override
    public Niveau save(Niveau niveau) {
        return niveauRepository.save(niveau);
    }

    @Override
    public void delete(Long id) {
        niveauRepository.deleteById(id);
    }
}
