package ftn.isa.sistemapoteka.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ftn.isa.sistemapoteka.Service.eRecipeService;
import ftn.isa.sistemapoteka.Repository.eRecipeRepository;

@Service
public class eRecipeServiceImpl implements eRecipeService{

    private eRecipeRepository eRecipeRepository;

    @Autowired
    public eRecipeServiceImpl(eRecipeRepository eRecipeRepository){
        this.eRecipeRepository = eRecipeRepository;
    }
}
