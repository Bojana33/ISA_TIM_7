package ftn.isa.sistemapoteka.repository;


import ftn.isa.sistemapoteka.model.Authority;
import ftn.isa.sistemapoteka.model.Dermatologist;
import ftn.isa.sistemapoteka.model.Pharmacist;
import ftn.isa.sistemapoteka.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    List<User> findUserByFirstNameContaining(String name);

    List<Dermatologist> findDermatologistsByFirstNameContainingAndLastNameContaining(String firstName, String lastName);

    List<Pharmacist> findPharmacistByFirstNameContainingAndLastNameContaining(String firstName, String lastName);

    List<Dermatologist> findUsersByAuthoritiesIsDermatologist(Authority authority);

    List<Pharmacist> findUsersByAuthoritiesIsPharmacist(Authority authority);

    @Transactional
    @Modifying
    @Query("UPDATE User u " +
            "SET u.enabled = TRUE WHERE u.email = ?1")
    int enableUser(String email);

    User findByEmailAndPassword(String email, String password) throws Exception;
}
