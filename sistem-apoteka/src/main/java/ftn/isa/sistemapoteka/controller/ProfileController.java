package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.model.Patient;
import ftn.isa.sistemapoteka.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@Controller
@RequestMapping(value="/profile")
public class ProfileController {
    private UserServiceImpl userService;

    @Autowired
    public ProfileController(UserServiceImpl userService) { this.userService = userService; }

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/p/{id}")
    public ModelAndView showPatientProfile(@PathVariable Long id, Model model) throws Exception {
        Patient patient = this.userService.findPatientById(id);
        if (patient == null) { throw new Exception("User does not exist"); }
        model.addAttribute("patient", patient);
        model.addAttribute("program", patient.getLoyaltyProgram());
        // TODO: Prikaz loyalty kategorije i poena
        return new ModelAndView("views/patientProfile");
    }

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/p/{id}/editProfile")
    public ModelAndView updateProfile(@PathVariable Long id, Model model) throws Exception{
        Patient patient = this.userService.findPatientById(id);
        if (patient == null) { throw new Exception("Patient with this id does not exist"); }
        model.addAttribute("patient", patient);

        return new ModelAndView("views/editProfile");
    }

    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping("/p/{id}/editProfile/submit")
    public ModelAndView updateProfile(@Valid @ModelAttribute Patient patient, Model model, BindingResult bindingResult,
                                      @PathVariable Long id) throws Exception{
        try {
            this.userService.updatePatientProfile(patient);
            model.addAttribute("patient", patient);
            // TODO: Namesti redirektovanje
            //return new ModelAndView("redirect:/profile/p/" + patient.getId() + "/editProfile");
            return new ModelAndView("views/editProfile");
        } catch (Exception e) {
            return new ModelAndView("views/home2");
        }
    }

}
