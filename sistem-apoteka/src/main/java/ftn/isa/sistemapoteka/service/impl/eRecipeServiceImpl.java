package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.model.Pharmacy;
import ftn.isa.sistemapoteka.model.eRecipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ftn.isa.sistemapoteka.service.eRecipeService;
import ftn.isa.sistemapoteka.repository.eRecipeRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class eRecipeServiceImpl implements eRecipeService{

    private eRecipeRepository eRecipeRepository;

    @Autowired
    public eRecipeServiceImpl(eRecipeRepository eRecipeRepository){
        this.eRecipeRepository = eRecipeRepository;
    }

    @Override
    public Map<Pharmacy,Double> calculatePrice(List<Pharmacy> pharmacies, List<Long> drugs) {
        Map<Pharmacy,Double> showList = new HashMap<>();
        for (Pharmacy pharmacy : pharmacies) {
            double price = 0.0;
            for (Long code: drugs){
                    price += pharmacy.getDrugs().get(code);
            }
            showList.put(pharmacy, price);
        }
        return showList;
    }

    @Override
    public eRecipe save(eRecipe eRecipe) {
        return this.eRecipeRepository.save(eRecipe);
    }


}
