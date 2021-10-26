package ftn.isa.sistemapoteka.repository;

import ftn.isa.sistemapoteka.model.LoyaltyProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoyaltyProgramRepository extends JpaRepository<LoyaltyProgram,Long> {

    @Override
    LoyaltyProgram getOne(Long aLong);
}
