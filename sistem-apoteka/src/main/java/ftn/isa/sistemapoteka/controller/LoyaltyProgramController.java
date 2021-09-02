package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.model.LoyaltyProgram;
import ftn.isa.sistemapoteka.service.LoyaltyProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "loyaltyProgram")
public class LoyaltyProgramController {

    private final LoyaltyProgramService loyaltyProgramService;

    @Autowired
    public LoyaltyProgramController(LoyaltyProgramService loyaltyProgramService) {
        this.loyaltyProgramService = loyaltyProgramService;
    }

    @GetMapping(value = "/updateLoyaltyProgram")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public ModelAndView updateLPForm(Model model) {
        LoyaltyProgram loyaltyProgram = this.loyaltyProgramService.getLP(1L);
        model.addAttribute(loyaltyProgram);
        return new ModelAndView("update-loyalty-program");
    }

    @PostMapping(value = "/updateLoyaltyProgram/submit")
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public ModelAndView updateLP(@ModelAttribute LoyaltyProgram loyaltyProgram) throws Exception {
        this.loyaltyProgramService.updateLP(loyaltyProgram);
        return new ModelAndView("redirect:/user/sys-admin/home");
    }
}
