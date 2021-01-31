package ftn.isa.sistemapoteka.Repository;


import ftn.isa.sistemapoteka.Entity.RegisteredUser;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RegisteredUserRepository extends JpaRepository<RegisteredUser,Long>{

    //List<User> findAll();
    RegisteredUser findByEmailAndPassword(String email, String password);
}
