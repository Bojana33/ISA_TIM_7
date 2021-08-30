package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.model.Dermatologist;
import ftn.isa.sistemapoteka.model.Patient;
import ftn.isa.sistemapoteka.model.Pharmacist;
import ftn.isa.sistemapoteka.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping(value="/profile")
public class ProfileController {

    private UserServiceImpl userService;

    @Autowired
    public ProfileController(UserServiceImpl userService) { this.userService = userService; }

    @PreAuthorize("hasRole('DERMATOLOGIST')")
    @GetMapping("/dermatologist/{id}")
    public ModelAndView showDermatologistProfile(@PathVariable Long id, Model model) throws Exception {
        Dermatologist dermatologist = this.userService.findDermatologistById(id);
        if (dermatologist == null) {
            throw new Exception("User does not exist");
        }
        model.addAttribute("dermatologist", dermatologist);
        return new ModelAndView("dermatologist-profile");
    }

    @PreAuthorize("hasRole('PHARMACIST')")
    @GetMapping("/pharmacist/{id}")
    public ModelAndView showPharmacistProfile(@PathVariable Long id, Model model) throws Exception {
        Pharmacist pharmacist = this.userService.findPharmacistById(id);
        if (pharmacist == null) {
            throw new Exception("User does not exist");
        }
        model.addAttribute("pharmacist", pharmacist);
        return new ModelAndView("pharmacist-profile");
    }
}
