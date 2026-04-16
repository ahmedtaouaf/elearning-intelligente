package com.example.Elearning.Impl;

import com.example.Elearning.Entity.ModuleCours;
import com.example.Elearning.Repository.ModuleCoursRepository;
import com.example.Elearning.Service.ModuleCoursService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleCoursServiceImpl implements ModuleCoursService {

    private final ModuleCoursRepository moduleCoursRepository;

    public ModuleCoursServiceImpl(ModuleCoursRepository moduleCoursRepository) {
        this.moduleCoursRepository = moduleCoursRepository;
    }

    @Override
    public List<ModuleCours> getAll() {
        return moduleCoursRepository.findAll();
    }

    @Override
    public ModuleCours getById(Long id) {
        return moduleCoursRepository.findById(id).orElseThrow();
    }

    @Override
    public ModuleCours save(ModuleCours moduleCours) {
        return moduleCoursRepository.save(moduleCours);
    }

    @Override
    public void delete(Long id) {
        moduleCoursRepository.deleteById(id);
    }
}
