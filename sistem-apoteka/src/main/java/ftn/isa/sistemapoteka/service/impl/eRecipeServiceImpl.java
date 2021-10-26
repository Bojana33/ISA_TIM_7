package ftn.isa.sistemapoteka.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ftn.isa.sistemapoteka.service.eRecipeService;
import ftn.isa.sistemapoteka.repository.eRecipeRepository;

@Service
public class eRecipeServiceImpl implements eRecipeService{

    private eRecipeRepository eRecipeRepository;

    @Autowired
    public eRecipeServiceImpl(eRecipeRepository eRecipeRepository){
        this.eRecipeRepository = eRecipeRepository;
    }
}
