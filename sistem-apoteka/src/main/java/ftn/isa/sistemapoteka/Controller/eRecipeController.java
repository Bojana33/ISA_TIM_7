package ftn.isa.sistemapoteka.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ftn.isa.sistemapoteka.Service.eRecipeService;

@Controller
@RequestMapping("eRecipes")
public class eRecipeController {

    private eRecipeService eRecipeService;

    @Autowired
    public eRecipeController(eRecipeService eRecipeService){
        this.eRecipeService = eRecipeService;
    }
}
