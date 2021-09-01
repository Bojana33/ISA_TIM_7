package ftn.isa.sistemapoteka.repository;


import ftn.isa.sistemapoteka.model.Pharmacist;
import ftn.isa.sistemapoteka.model.Pharmacy;
import ftn.isa.sistemapoteka.model.User;
import ftn.isa.sistemapoteka.model.UserRole;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

    User findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u " +
            "SET u.enabled = TRUE WHERE u.email = ?1")
    int enableUser(String email);

    User findByEmailAndPassword(String email, String password);

    List<User> findAllByUserRole(UserRole role);

}
