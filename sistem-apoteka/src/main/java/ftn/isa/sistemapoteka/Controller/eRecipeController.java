package ftn.isa.sistemapoteka.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ftn.isa.sistemapoteka.Service.eRecipeService;

@RestController
public class eRecipeController {

    @Autowired
    private eRecipeService eRecipeService;
}
