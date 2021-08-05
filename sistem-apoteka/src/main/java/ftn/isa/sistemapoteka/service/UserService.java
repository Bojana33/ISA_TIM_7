package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.Patient;
import ftn.isa.sistemapoteka.model.User;
import ftn.isa.sistemapoteka.model.UserRequest;

import java.util.List;

public interface UserService {
    User findById(Long id);
    User findByEmail(String email);
    List<User> findAll ();
    Patient savePatient(UserRequest userRequest);
    int enableUser(String email);
    String confirmToken(String token);
    String buildEmail(String name, String link);
}
