package ftn.isa.sistemapoteka.Repository;

import ftn.isa.sistemapoteka.Entity.LoyaltyProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoyaltyProgramRepository extends JpaRepository<LoyaltyProgram,Long> {
}
