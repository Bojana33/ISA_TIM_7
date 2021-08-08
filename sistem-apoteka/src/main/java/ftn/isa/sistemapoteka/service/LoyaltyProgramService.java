package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.LoyaltyProgram;

public interface LoyaltyProgramService {

    LoyaltyProgram saveLP(LoyaltyProgram loyaltyProgram) throws Exception;

    LoyaltyProgram getLP(Long id);
}
