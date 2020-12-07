package ftn.isa.sistemapoteka.Repository;


import ftn.isa.sistemapoteka.Entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

    //List<User> findAll();
    User findByEmailAndPassword(String email, String password);
}
