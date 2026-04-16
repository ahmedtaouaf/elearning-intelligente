package com.example.Elearning.Impl;

import com.example.Elearning.Entity.Matiere;
import com.example.Elearning.Repository.MatiereRepository;
import com.example.Elearning.Service.MatiereService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatiereServiceImpl implements MatiereService {

    private final MatiereRepository matiereRepository;

    public MatiereServiceImpl(MatiereRepository matiereRepository) {
        this.matiereRepository = matiereRepository;
    }

    @Override
    public List<Matiere> getAll() {
        return matiereRepository.findAll();
    }

    @Override
    public Matiere getById(Long id) {
        return matiereRepository.findById(id).orElseThrow();
    }

    @Override
    public Matiere save(Matiere matiere) {
        return matiereRepository.save(matiere);
    }

    @Override
    public void delete(Long id) {
        matiereRepository.deleteById(id);
    }
}
