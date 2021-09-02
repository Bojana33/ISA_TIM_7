package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.LoyaltyProgram;

public interface LoyaltyProgramService {

    LoyaltyProgram getLP(Long id);

    LoyaltyProgram updateLP(LoyaltyProgram loyaltyProgram) throws Exception;
}
