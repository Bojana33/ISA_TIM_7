package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.Pharmacy;
import ftn.isa.sistemapoteka.model.eRecipe;

import java.util.List;
import java.util.Map;

public interface eRecipeService {

    Map<Pharmacy,Double> calculatePrice(List<Pharmacy> pharmacy, List<Long> drugs);

    eRecipe save(eRecipe eRecipe);
}
