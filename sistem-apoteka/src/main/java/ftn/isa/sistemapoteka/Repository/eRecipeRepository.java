package ftn.isa.sistemapoteka.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ftn.isa.sistemapoteka.Entity.eRecipe;
import org.springframework.stereotype.Repository;

@Repository
public interface eRecipeRepository extends JpaRepository<eRecipe, Long> {
}
