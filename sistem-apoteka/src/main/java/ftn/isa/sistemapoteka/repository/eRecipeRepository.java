package ftn.isa.sistemapoteka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ftn.isa.sistemapoteka.model.eRecipe;
import org.springframework.stereotype.Repository;

@Repository
public interface eRecipeRepository extends JpaRepository<eRecipe, Long> {
}
