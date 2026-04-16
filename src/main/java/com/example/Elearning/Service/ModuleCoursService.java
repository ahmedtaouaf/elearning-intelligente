package com.example.Elearning.Service;

import com.example.Elearning.Entity.ModuleCours;

import java.util.List;

public interface ModuleCoursService {
    List<ModuleCours> getAll();
    ModuleCours getById(Long id);
    ModuleCours save(ModuleCours moduleCours);
    void delete(Long id);
}
