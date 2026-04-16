package com.example.Elearning.controller;

import com.example.Elearning.Entity.Filiere;
import com.example.Elearning.Entity.Matiere;
import com.example.Elearning.Entity.ModuleCours;
import com.example.Elearning.Entity.Niveau;
import com.example.Elearning.Repository.FiliereRepository;
import com.example.Elearning.Repository.MatiereRepository;
import com.example.Elearning.Repository.NiveauRepository;
import com.example.Elearning.Service.ModuleCoursService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/modules")
public class AdminModuleController {

    private final ModuleCoursService moduleCoursService;
    private final MatiereRepository matiereRepository;
    private final NiveauRepository niveauRepository;
    private final FiliereRepository filiereRepository;

    public AdminModuleController(ModuleCoursService moduleCoursService,
                                 MatiereRepository matiereRepository,
                                 NiveauRepository niveauRepository,
                                 FiliereRepository filiereRepository) {
        this.moduleCoursService = moduleCoursService;
        this.matiereRepository = matiereRepository;
        this.niveauRepository = niveauRepository;
        this.filiereRepository = filiereRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("modules", moduleCoursService.getAll());
        model.addAttribute("moduleCours", new ModuleCours());
        model.addAttribute("matieres", matiereRepository.findAll());
        model.addAttribute("niveaux", niveauRepository.findAll());
        model.addAttribute("filieres", filiereRepository.findAll());
        return "admin/modules/list";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute ModuleCours moduleCours) {
        Matiere matiere = matiereRepository.findById(moduleCours.getMatiere().getId()).orElseThrow();
        Niveau niveau = niveauRepository.findById(moduleCours.getNiveau().getId()).orElseThrow();
        Filiere filiere = filiereRepository.findById(moduleCours.getFiliere().getId()).orElseThrow();

        moduleCours.setMatiere(matiere);
        moduleCours.setNiveau(niveau);
        moduleCours.setFiliere(filiere);

        moduleCoursService.save(moduleCours);
        return "redirect:/admin/modules";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        moduleCoursService.delete(id);
        return "redirect:/admin/modules";
    }
}
