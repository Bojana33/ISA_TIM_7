package ftn.isa.sistemapoteka.service.impl;

import ftn.isa.sistemapoteka.model.Authority;
import ftn.isa.sistemapoteka.model.ConfirmationToken;
import ftn.isa.sistemapoteka.repository.AuthorityRepository;
import ftn.isa.sistemapoteka.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    private AuthorityRepository authorityRepository;

    private ConfirmationTokenServiceImpl confirmationTokenService;



    @Autowired
    public AuthorityServiceImpl(AuthorityRepository authorityRepository,
                                ConfirmationTokenServiceImpl confirmationTokenService){
        this.authorityRepository=authorityRepository;
        this.confirmationTokenService=confirmationTokenService;
    }

    @Override
    public List<Authority> findById(Long id) {
        Authority auth = this.authorityRepository.getOne(id);
        List<Authority> auths = new ArrayList<>();
        auths.add(auth);
        return auths;
    }

    @Override
    public List<Authority> findByName(String name) {
        Authority auth = this.authorityRepository.findByName(name);
        List<Authority> auths = new ArrayList<>();
        auths.add(auth);
        return auths;
    }
}
