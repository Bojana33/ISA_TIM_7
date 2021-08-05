package ftn.isa.sistemapoteka.repository;

import ftn.isa.sistemapoteka.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority,Long> {
    Authority findByName(String name);
}
