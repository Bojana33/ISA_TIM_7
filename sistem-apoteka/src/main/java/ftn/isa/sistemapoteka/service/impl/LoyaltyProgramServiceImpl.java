package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.model.LoyaltyProgram;
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

    @Override
    public LoyaltyProgram saveLP(LoyaltyProgram loyaltyProgram) throws Exception{
        if (loyaltyProgram.getGoldPoints() < loyaltyProgram.getSilverPoints() || loyaltyProgram.getGoldPoints() < loyaltyProgram.getRegularPoints()){
            throw new Exception("Gold points must be greater than silver and regular points");
        }
        if (loyaltyProgram.getSilverPoints() < loyaltyProgram.getRegularPoints() || loyaltyProgram.getSilverPoints() > loyaltyProgram.getGoldPoints()){
            throw new Exception("Silver points must be greater than regular and less than gold points");
        }
        if (loyaltyProgram.getRegularPoints() > loyaltyProgram.getSilverPoints() || loyaltyProgram.getRegularPoints() > loyaltyProgram.getGoldPoints()){
            throw new Exception("Regular points must be less than silver and gold points");
        }
        return this.loyaltyProgramRepository.save(loyaltyProgram);
    }

    public LoyaltyProgram save(LoyaltyProgram loyaltyProgram) {
        return this.loyaltyProgramRepository.save(loyaltyProgram);
    }

    @Override
    public LoyaltyProgram getLP(Long id) {
        return this.loyaltyProgramRepository.getOne(id);
    }
}
