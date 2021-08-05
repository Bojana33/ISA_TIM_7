package ftn.isa.sistemapoteka.service;

import ftn.isa.sistemapoteka.model.Authority;

import java.util.List;

public interface AuthorityService {
    List<Authority> findById(Long id);
    List<Authority> findByName(String name);
}