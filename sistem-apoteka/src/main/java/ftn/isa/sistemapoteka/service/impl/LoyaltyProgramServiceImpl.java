package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.repository.LoyaltyProgramRepository;
import ftn.isa.sistemapoteka.service.LoyaltyProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoyaltyProgramServiceImpl implements LoyaltyProgramService {

    private LoyaltyProgramRepository loyaltyProgramRepository;

    @Autowired
    public LoyaltyProgramServiceImpl(LoyaltyProgramRepository loyaltyProgramRepository){
        this.loyaltyProgramRepository = loyaltyProgramRepository;
    }
}
