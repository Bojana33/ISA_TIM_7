package ftn.isa.sistemapoteka.Service.impl;

import ftn.isa.sistemapoteka.Repository.RegisteredUserRepository;
import ftn.isa.sistemapoteka.Service.RegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisteredUserServiceImpl implements RegisteredUserService {

    private RegisteredUserRepository registeredUserRepository;

    @Autowired
    public RegisteredUserServiceImpl(RegisteredUserRepository registeredUserRepository){
        this.registeredUserRepository = registeredUserRepository;
    }
}
